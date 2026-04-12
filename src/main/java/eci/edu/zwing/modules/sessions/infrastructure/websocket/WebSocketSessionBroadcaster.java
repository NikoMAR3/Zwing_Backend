package eci.edu.zwing.modules.sessions.infrastructure.websocket;


import eci.edu.zwing.modules.sessions.application.ports.outbound.SessionBroadcaster;
import eci.edu.zwing.modules.sessions.infrastructure.ports.redis.BroadcastInfo;
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
    public void broadcast(BroadcastInfo info) {
        messagingTemplate.convertAndSend(
                "/topic/sessions/" + info.correlationId(),
                (Object) info.envelope()
        );
    }
}