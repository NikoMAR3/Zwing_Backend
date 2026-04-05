package eci.edu.zwing.modules.sessions.infrastructure.ports;

import eci.edu.zwing.modules.sessions.domain.*;
import eci.edu.zwing.modules.sessions.domain.ports.outbound.SessionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "sessions";

    public SessionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Session session) {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (id, tool_id, user_id, created_at, last_activity_at, status, metadata) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::jsonb)";

        jdbcTemplate.update(sql,
                session.getId().value(),
                session.getToolId().value(),
                session.getUserId(),
                session.getCreatedAt(),
                session.getLastActivityAt(),
                session.getStatus().name(),
                convertMetadataToJson(session.getMetadata())
        );
    }

    @Override
    public void update(Session session) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET status = ?, last_activity_at = ?, metadata = ?::jsonb " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                session.getStatus().name(),
                session.getLastActivityAt(),
                convertMetadataToJson(session.getMetadata()),
                session.getId().value()
        );
    }

    @Override
    public Optional<Session> findById(SessionId id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        List<Session> sessions = jdbcTemplate.query(sql, new SessionRowMapper(), id.value());
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions.get(0));
    }


    @Override
    public List<Session> findByUserId(String userId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new SessionRowMapper(), userId);
    }

    @Override
    public List<Session> findByToolId(ToolId toolId) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE tool_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new SessionRowMapper(), toolId.value());
    }

    @Override
    public List<Session> findActiveByToolId(ToolId toolId) {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE tool_id = ? AND status = 'ACTIVE' ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new SessionRowMapper(), toolId.value());
    }

    @Override
    public List<Session> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new SessionRowMapper());
    }

    @Override
    public void delete(SessionId id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        jdbcTemplate.update(sql, id.value());
    }

    private String convertMetadataToJson(Map<String, Object> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            return "{}";
        }
        StringBuilder json = new StringBuilder("{");
        metadata.forEach((key, value) -> {
            json.append("\"").append(key).append("\":");
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else if (value instanceof Number || value instanceof Boolean) {
                json.append(value);
            } else {
                json.append("\"").append(value.toString()).append("\"");
            }
            json.append(",");
        });
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }

    private static class SessionRowMapper implements RowMapper<Session> {
        @Override
        public Session mapRow(ResultSet rs, int rowNum) throws SQLException {
            String id = rs.getString("id");
            String toolId = rs.getString("tool_id");
            String userId = rs.getString("user_id");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            var lastActivityAt = rs.getTimestamp("last_activity_at").toLocalDateTime();
            String status = rs.getString("status");
            String metadataJson = rs.getString("metadata");

            Map<String, Object> metadata = parseMetadataJson(metadataJson);

            return Session.fromPersistence(
                    SessionId.of(id),
                    ToolId.of(toolId),
                    userId,
                    createdAt,
                    lastActivityAt,
                    SessionStatus.valueOf(status),
                    metadata
            );
        }

        private Map<String, Object> parseMetadataJson(String json) {
            Map<String, Object> metadata = new java.util.HashMap<>();
            if (json == null || json.equals("{}")) {
                return metadata;
            }
            // Simple parsing (en producción usa Jackson o similar)
            String content = json.substring(1, json.length() - 1);
            if (content.isEmpty()) return metadata;

            String[] pairs = content.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].replaceAll("\"", "").trim();
                    String value = keyValue[1].replaceAll("\"", "").trim();
                    metadata.put(key, value);
                }
            }
            return metadata;
        }
    }
}
