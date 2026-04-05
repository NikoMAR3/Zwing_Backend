package eci.edu.zwing.modules.sessions.infrastructure.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import eci.edu.zwing.modules.sessions.application.ports.outbound.RealtimeCommandDispatcher;
import eci.edu.zwing.modules.sessions.infrastructure.realtime.RealtimeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GenericWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private RealtimeCommandDispatcher commandDispatcher;

    private static final Map<String, Set<WebSocketSession>> activeSessions = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = extractSessionId(session);
        activeSessions.computeIfAbsent(sessionId, k -> ConcurrentHashMap.newKeySet())
                .add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = extractSessionId(session);
        activeSessions.getOrDefault(sessionId, new HashSet<>()).remove(session);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        String sessionId = extractSessionId(session);
        try {
            RealtimeCommand command = mapper.readValue(payload, RealtimeCommand.class);
            commandDispatcher.dispatch(sessionId, command);

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcastToSession(String sessionId, Object state) throws IOException {
        Set<WebSocketSession> sessions = activeSessions.getOrDefault(sessionId, new HashSet<>());
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(state)));
            }
        }
    }

    private String extractSessionId(WebSocketSession session) {
        // URL: ws://localhost:8080/ws/sessions/rack123
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        return parts[parts.length - 1];
    }
}
