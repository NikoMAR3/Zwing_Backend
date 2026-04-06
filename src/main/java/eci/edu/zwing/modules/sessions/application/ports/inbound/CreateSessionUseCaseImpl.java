package eci.edu.zwing.modules.sessions.application.ports.inbound;

import eci.edu.zwing.modules.sessions.domain.Session;
import eci.edu.zwing.modules.sessions.domain.ToolId;
import eci.edu.zwing.modules.sessions.domain.ports.inbound.CreateSessionUseCase;
import eci.edu.zwing.modules.sessions.domain.ports.outbound.SessionRepository;
import eci.edu.zwing.modules.sessions.infrastructure.dtos.SessionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionUseCaseImpl  implements CreateSessionUseCase {

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public String execute(SessionsRequest.CreateSessionRequest createSessionRequest) {
        Session session = Session.create(
            ToolId.of(createSessionRequest.toolId()),
            createSessionRequest.userId(),
            createSessionRequest.metadata()
            );
        sessionRepository.save(session);
        return session.getId().value();
    }
}
