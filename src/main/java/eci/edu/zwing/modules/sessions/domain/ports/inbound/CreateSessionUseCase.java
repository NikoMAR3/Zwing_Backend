package eci.edu.zwing.modules.sessions.domain.ports.inbound;

import eci.edu.zwing.modules.sessions.infrastructure.dtos.SessionsRequest;

public interface CreateSessionUseCase {
    void execute(SessionsRequest.CreateSessionRequest createSessionRequest);
}
