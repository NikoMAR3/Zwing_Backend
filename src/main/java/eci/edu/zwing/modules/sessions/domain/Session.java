package eci.edu.zwing.modules.sessions.domain;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Agregado raíz que representa una sesión realtime genérica
 */
public class Session {

    private final SessionId id;
    private final ToolId toolId;
    private final String userId;
    private final LocalDateTime createdAt;
    private LocalDateTime lastActivityAt;
    private SessionStatus status;
    private final Map<String, Object> metadata;

    private Session(SessionId id, ToolId toolId, String userId,
                    LocalDateTime createdAt, LocalDateTime lastActivityAt, // ← agregar
                    SessionStatus status, Map<String, Object> metadata) {
        this.id = Objects.requireNonNull(id);
        this.toolId = Objects.requireNonNull(toolId);
        this.userId = Objects.requireNonNull(userId);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.lastActivityAt = Objects.requireNonNull(lastActivityAt); // ← usar el parámetro
        this.status = Objects.requireNonNull(status);
        this.metadata = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
    }

    public static Session create(ToolId toolId, String userId, Map<String, Object> metadata) {
        return new Session(
                SessionId.generate(),
                toolId,
                userId,
                LocalDateTime.now(),
                LocalDateTime.now(),
                SessionStatus.ACTIVE,
                metadata
        );
    }
    public static Session fromPersistence(SessionId id, ToolId toolId, String userId,
                                          LocalDateTime createdAt, LocalDateTime lastActivityAt,
                                          SessionStatus status, Map<String, Object> metadata) {
        return new Session(id, toolId, userId, createdAt, lastActivityAt, status, metadata);
    }


    public void recordActivity() {
        this.lastActivityAt = LocalDateTime.now();
    }

    public void close() {
        this.status = SessionStatus.CLOSED;
    }

    public boolean isActive() {
        return status == SessionStatus.ACTIVE;
    }

    // Getters
    public SessionId getId() { return id; }
    public ToolId getToolId() { return toolId; }
    public String getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastActivityAt() { return lastActivityAt; }
    public SessionStatus getStatus() { return status; }
    public Map<String, Object> getMetadata() { return new HashMap<>(metadata); }
}