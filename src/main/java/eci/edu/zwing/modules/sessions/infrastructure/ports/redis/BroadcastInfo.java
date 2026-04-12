package eci.edu.zwing.modules.sessions.infrastructure.ports.redis;

import java.util.Map;

public record BroadcastInfo(String correlationId, Map<String,Object> envelope) {
}
