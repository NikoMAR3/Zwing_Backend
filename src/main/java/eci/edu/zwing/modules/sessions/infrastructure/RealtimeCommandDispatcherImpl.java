package eci.edu.zwing.modules.sessions.infrastructure;

import eci.edu.zwing.modules.sessions.domain.RealtimeEvent;
import eci.edu.zwing.modules.sessions.application.ports.RealtimeCommandDispatcher;
import eci.edu.zwing.modules.sessions.application.ports.RealtimeEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealtimeCommandDispatcherImpl implements RealtimeCommandDispatcher {

    @Autowired
    private RealtimeEventPublisher eventPublisher;

    @Override
    public void dispatch(String sessionId, RealtimeCommand command) throws Exception {
        RealtimeEvent event = new RealtimeEvent(
                this,
                command.entityType(),
                sessionId,
                command.action(),
                command.data()
        );
        eventPublisher.publishEvent(event);
    }
}