package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.persistence;

import eci.edu.zwing.modules.channelrack.application.mappers.SnapshotMapper;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.model.DomainEvent;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.EventStore;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.OutboxStore;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.SnapshotStore;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus.EventEnvelope;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.snapshots.Snapshot;
//import org.springframework.security.core.context.SecurityContextHolder;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.shared.UserIdProvider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ChannelRackRepositoryImpl implements ChannelRackRepository {

    private final EventStore eventStore;
    private final SnapshotStore snapshotStore;
    private final OutboxStore outboxStore;// o un puerto tuyo
    private final UserIdProvider userIdProvider;

    public ChannelRackRepositoryImpl(EventStore eventStore, SnapshotStore snapshotStore, OutboxStore outboxStore, UserIdProvider userIdProvider) {
        this.eventStore = eventStore;
        this.snapshotStore = snapshotStore;
        this.outboxStore = outboxStore;
        this.userIdProvider = userIdProvider;
    }

    @Transactional
    public void save(ChannelRack rack, String correlationId) {
        List<DomainEvent> newEvents = rack.getUncommittedEvents();

        String userId = userIdProvider.getCurrentUserId();

        long version = rack.getVersion() - newEvents.size();

        List<EventEnvelope> envelopes = new ArrayList<>();

        for (DomainEvent event : newEvents) {
            version++;

            envelopes.add(new EventEnvelope(
                    UUID.randomUUID(),
                    rack.getChannelRackId(),
                    version,
                    event.getClass().getSimpleName(),
                    event,
                    userId,
                    correlationId,
                    new java.sql.Timestamp(System.currentTimeMillis()),
                    Map.of()
            ));
        }

        eventStore.append(envelopes);
        outboxStore.append(envelopes);

        rack.markEventsAsCommitted();
        if (shouldCreateSnapshot(rack)) {
            snapshotStore.save(SnapshotMapper.entityToSnapshot(rack));
        }

    }

    private boolean shouldCreateSnapshot(ChannelRack rack) {
        long snapshotVersion = snapshotStore.findLatest(rack.getChannelRackId()).map(Snapshot.ChannelRackSnapshot::version).orElse(0L);
        return rack.getVersion() - snapshotVersion >= 50;
    }

    @Override
    public ChannelRack load(String channelRackId) {
        Snapshot.ChannelRackSnapshot snapshot = snapshotStore.findLatest(channelRackId).orElse(null);

        Long fromVersion = snapshot != null ? snapshot.version() : 0;
        ChannelRack rack = snapshot != null
                ? toChannelRack(snapshot)
                : new ChannelRack(); //revisar

        List<DomainEvent> events = eventStore
                .loadEnvelopes(channelRackId, fromVersion)
                .stream()
                .map(EventEnvelope::payload)
                .toList();

        events.forEach(rack::applyEvent);

        if (events.isEmpty() && snapshot == null) {
            throw new IllegalArgumentException("ChannelRack not found: " + channelRackId);
        }
        return rack;
    }

    private ChannelRack toChannelRack(Snapshot.ChannelRackSnapshot snapshot) {
        return SnapshotMapper.snapshotToEntity(snapshot);
    }
}