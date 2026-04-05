package eci.edu.zwing.modules.sessions.application.ports.outbound;

import java.util.Map;

public interface SessionBroadcaster {
    void broadcast(String correlationId, String action, Map<String, Object> data);
}
