package eci.edu.zwing.modules.sessions.infrastructure.websocket;


import eci.edu.zwing.modules.sessions.application.ports.outbound.SessionBroadcaster;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketSessionBroadcaster implements SessionBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketSessionBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcast(String correlationId, String action, Map<String, Object> data) {

        Map<String, Object> payload = Map.of(
                "action", action,
                "data", data
        );

        messagingTemplate.convertAndSend(
                "/topic/session/" + correlationId,
                (Object) payload
        );
    }
}