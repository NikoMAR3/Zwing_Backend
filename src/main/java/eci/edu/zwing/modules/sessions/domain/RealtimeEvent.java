package eci.edu.zwing.modules.sessions.domain;

import org.springframework.context.ApplicationEvent;
import java.util.Map;

/**
 * Evento que publica el módulo realtime cuando recibe un comando WebSocket
 */
public class RealtimeEvent extends ApplicationEvent {

    private final String entityType;
    private final String sessionId;
    private final String action;
    private final Map<String, Object> data;

    public RealtimeEvent(Object source, String entityType, String sessionId,
                         String action, Map<String, Object> data) {
        super(source);
        this.entityType = entityType;
        this.sessionId = sessionId;
        this.action = action;
        this.data = data;
    }

    public String getEntityType() { return entityType; }
    public String getSessionId() { return sessionId; }
    public String getAction() { return action; }
    public Map<String, Object> getData() { return data; }
}