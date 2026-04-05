package eci.edu.zwing.modules.sessions.infrastructure.dtos;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Sealed interface que agrupa todos los responses del módulo de sesiones
 */
public sealed interface SessionsResponse permits
        SessionsResponse.SessionResponse,
        SessionsResponse.ConnectedUsersStatisticsResponse,
        SessionsResponse.SessionCreatedResponse,
        SessionsResponse.ErrorResponse {

    record SessionResponse(
            String id,
            String toolId,
            String userId,
            LocalDateTime createdAt,
            LocalDateTime lastActivityAt,
            String status,
            Map<String, Object> metadata
    ) implements SessionsResponse {}

    record SessionCreatedResponse(
            String id,
            String toolId,
            String userId,
            LocalDateTime createdAt,
            String status
    ) implements SessionsResponse {}

    record ConnectedUsersStatisticsResponse(
            String toolId,
            int totalConnected,
            Map<String, Integer> userConnectionsByTool
    ) implements SessionsResponse {}

    record ErrorResponse(
            String code,
            String message,
            LocalDateTime timestamp
    ) implements SessionsResponse {}
}
