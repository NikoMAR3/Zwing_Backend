package eci.edu.zwing.modules.sessions.infrastructure.websocket;

import eci.edu.zwing.modules.sessions.application.ports.outbound.RealtimeCommandDispatcher;
import eci.edu.zwing.modules.sessions.infrastructure.realtime.RealtimeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class SessionStompController {

    @Autowired
    private RealtimeCommandDispatcher commandDispatcher;

    @MessageMapping("/sessions/{sessionId}")
    public void handleCommand(
            @DestinationVariable String sessionId,
            @Payload RealtimeCommand command) throws Exception {
        commandDispatcher.dispatch(sessionId, command);
    }
}
