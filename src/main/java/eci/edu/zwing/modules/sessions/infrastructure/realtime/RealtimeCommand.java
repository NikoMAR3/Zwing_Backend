package eci.edu.zwing.modules.sessions.infrastructure.realtime;

public record RealtimeCommand(
        String entityType,
        String action,
        String userId,
        java.util.Map<String, Object> data
) {}