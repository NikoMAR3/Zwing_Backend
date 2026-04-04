package eci.edu.zwing.modules.channelrack.domain.ports.outbound;

import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus.EventEnvelope;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus.OutboxEntry;

import java.util.List;
import java.util.UUID;

public interface OutboxStore {
    void append(List<EventEnvelope> envelopes);
    List<OutboxEntry> findUnpublished(int limit);
    void markPublished(UUID id);
}