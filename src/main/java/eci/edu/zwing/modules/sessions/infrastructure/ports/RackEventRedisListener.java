package eci.edu.zwing.modules.sessions.infrastructure.ports;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eci.edu.zwing.modules.sessions.application.ports.outbound.SessionBroadcaster;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RackEventRedisListener implements MessageListener {

    private final ObjectMapper mapper;
    private final SessionBroadcaster sessionBroadcaster;

    public RackEventRedisListener(ObjectMapper mapper, SessionBroadcaster sessionBroadcaster) {
        this.mapper = mapper;
        this.sessionBroadcaster = sessionBroadcaster;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String payload = new String(message.getBody());
        Map<String, Object> eventData;

        try {
            eventData = mapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String correlationId = (String) eventData.get("correlationId");
        String action = (String) eventData.get("action");
        Map<String, Object> data = (Map<String, Object>) eventData.get("data");

        sessionBroadcaster.broadcast(correlationId, action, data);
    }
}
