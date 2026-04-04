package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus;


import com.fasterxml.jackson.databind.ObjectMapper;
import eci.edu.zwing.modules.channelrack.domain.model.DomainEvent;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.EventStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class PostgreSQLEventStore implements EventStore {

    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;

    public PostgreSQLEventStore(JdbcTemplate jdbc, ObjectMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void append(List<EventEnvelope> envelopes) {
        String sql = """
            INSERT INTO events 
            (id, aggregate_id, version, event_type, payload, user_id, session_id, occurred_on, metadata, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
            """;

        for (EventEnvelope envelope : envelopes) {
            try {
                jdbc.update(sql,
                        envelope.eventId().toString(),
                        envelope.rackId(),
                        envelope.version(),
                        envelope.eventType(),
                        mapper.writeValueAsString(envelope.payload()),
                        envelope.userId(),
                        envelope.sessionId(),
                        java.sql.Timestamp.from(envelope.occurredOn()),
                        mapper.writeValueAsString(envelope.metadata())
                );
            } catch (Exception e) {
                throw new RuntimeException("Error appending event: " + envelope.eventId(), e);
            }
        }
    }

    @Override
    public List<EventEnvelope> loadEnvelopes(String aggregateId) {
        String sql = """
            SELECT id, aggregate_id, version, event_type, payload, user_id, session_id, occurred_on, metadata
            FROM events
            WHERE aggregate_id = ?
            ORDER BY version ASC
            """;

        return jdbc.query(sql, (rs, rowNum) -> {
            try {
                return mapRowToEventEnvelope(rs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, aggregateId);
    }

    @Override
    public List<EventEnvelope> loadEnvelopes(String aggregateId, long fromVersion) {
        String sql = """
            SELECT id, aggregate_id, version, event_type, payload, user_id, session_id, occurred_on, metadata
            FROM events
            WHERE aggregate_id = ? AND version > ?
            ORDER BY version ASC
            """;

        return jdbc.query(sql, (rs, rowNum) -> {
                    try {
                        return mapRowToEventEnvelope(rs);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                aggregateId, fromVersion);
    }

    private EventEnvelope mapRowToEventEnvelope(java.sql.ResultSet rs) throws Exception {
        return new EventEnvelope(
                UUID.fromString(rs.getString("id")),
                rs.getString("aggregate_id"),
                rs.getLong("version"),
                rs.getString("event_type"),
                mapper.readValue(rs.getString("payload"), DomainEvent.class),
                rs.getString("user_id"),
                rs.getString("session_id"),
                rs.getTimestamp("occurred_on").toInstant(),
                mapper.readValue(rs.getString("metadata"), java.util.Map.class)
        );
    }
}