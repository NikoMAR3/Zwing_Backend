package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus;

import java.time.Instant;
import java.util.UUID;

public record OutboxEntry(
        UUID id,
        String aggregateId,
        String eventType,
        String payload,
        String correlationId,// JSON serializado
        boolean published,
        Instant occurredOn,
        Instant publishedAt  // null hasta que se publique
) {}
