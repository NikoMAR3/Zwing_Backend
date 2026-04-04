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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ChannelRackRepositoryImpl implements ChannelRackRepository {

    private final EventStore eventStore;
    private final SnapshotStore snapshotStore;
    private final OutboxStore outboxStore;
    private final SecurityContextHolder security; // o un puerto tuyo

    public ChannelRackRepositoryImpl(EventStore eventStore, SnapshotStore snapshotStore, OutboxStore outboxStore, SecurityContextHolder security) {
        this.eventStore = eventStore;
        this.snapshotStore = snapshotStore;
        this.outboxStore = outboxStore;
        this.security = security;
    }

    @Transactional
    public void save(ChannelRack rack) {
        List<DomainEvent> newEvents = rack.getUncommittedEvents();

        String userId = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        ServletRequestAttributes attrs = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        String sessionId = attrs.getRequest().getHeader("X-Session-Id");

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
                    sessionId,
                    Instant.now(),
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