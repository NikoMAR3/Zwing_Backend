package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eci.edu.zwing.modules.channelrack.domain.model.DomainEvent;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record EventEnvelope(
        UUID eventId,
        String rackId,
        long version,
        String eventType,
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
        DomainEvent payload,
        String userId,
        String correlationId,
        java.sql.Timestamp occurredOn,
        Map<String, String> metadata  // extensible: IP, deviceId, etc.
) {}