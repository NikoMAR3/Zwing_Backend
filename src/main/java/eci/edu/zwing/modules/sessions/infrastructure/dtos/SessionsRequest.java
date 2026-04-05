package eci.edu.zwing.modules.sessions.infrastructure.dtos;

import java.util.Map;

public sealed interface SessionsRequest {
    record CreateSessionRequest(String toolId,String userId,Map<String, Object> metadata) implements SessionsRequest {}
    record RecordSessionActivityRequest(String sessionId) implements SessionsRequest {}
    record GetConnectedUsersStatisticsRequest(String toolId) implements SessionsRequest {}
    record CloseSessionRequest(String sessionId,String reason) implements SessionsRequest {}
}
