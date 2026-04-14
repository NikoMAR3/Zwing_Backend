package eci.edu.zwing.modules.sessions.infrastructure.ports.inbound.realtime;

public record RealtimeCommand(
        String entityType,
        String action,
        String userId,
        java.util.Map<String, Object> data
) {}