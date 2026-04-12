package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.OutboxStore;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PostgresOutboxStore implements OutboxStore {

    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;

    public PostgresOutboxStore(JdbcTemplate jdbc, ObjectMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public void append(List<EventEnvelope> envelopes) {
        for (EventEnvelope envelope : envelopes) {
            jdbc.update("""
            INSERT INTO outbox_events (id, aggregate_id, correlation_id, event_type, payload, published, occurred_on)
            VALUES (?, ?, ?, ?, ?::jsonb, false, ?)
            """,
                    envelope.eventId(),
                    envelope.rackId(),
                    envelope.correlationId(),
                    envelope.eventType(),
                    mapper.writeValueAsString(envelope),
                    envelope.occurredOn()
            );
        }
    }

    @Override
    public List<OutboxEntry> findUnpublished(int limit) {
        return jdbc.query("""
        SELECT id, aggregate_id, correlation_id, event_type, payload, published, occurred_on, published_at
        FROM outbox_events
        WHERE published = false
        ORDER BY occurred_on ASC
        LIMIT ?
        """,
                (rs, rowNum) -> new OutboxEntry(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("aggregate_id"),
                        rs.getString("event_type"),
                        rs.getString("payload"),
                        rs.getString("correlation_id"),
                        rs.getBoolean("published"),
                        rs.getTimestamp("occurred_on").toInstant(),
                        rs.getTimestamp("published_at") != null
                                ? rs.getTimestamp("published_at").toInstant()
                                : null
                ),
                limit
        );
    }

    @Override
    public void markPublished(UUID id) {
        jdbc.update("""
            UPDATE outbox_events
            SET published = true, published_at = now()
            WHERE id = ?
            """,
                id
        );
    }
}