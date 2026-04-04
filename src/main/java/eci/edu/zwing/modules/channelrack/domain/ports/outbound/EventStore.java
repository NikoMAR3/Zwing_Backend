package eci.edu.zwing.modules.channelrack.domain.ports.outbound;

import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus.EventEnvelope;

import java.util.List;

public interface EventStore {
    void append(List<EventEnvelope> envelopes);
    List<EventEnvelope> loadEnvelopes(String aggregateId);
    List<EventEnvelope> loadEnvelopes(String aggregateId, long fromVersion);
}