package eci.edu.zwing.modules.sessions.infrastructure;

public record RealtimeCommand(
        String entityType,
        String action,
        java.util.Map<String, Object> data
) {}