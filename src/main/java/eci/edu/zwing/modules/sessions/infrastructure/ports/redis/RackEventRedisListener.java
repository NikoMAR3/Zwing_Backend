package eci.edu.zwing.modules.sessions.infrastructure.ports.redis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eci.edu.zwing.modules.sessions.application.ports.outbound.SessionBroadcaster;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RackEventRedisListener implements MessageListener {

    private final ObjectMapper mapper;
    private final SessionBroadcaster sessionBroadcaster;
    private final RedisMessageListenerContainer container;

    public RackEventRedisListener(
            ObjectMapper mapper,
            SessionBroadcaster sessionBroadcaster,
            @Qualifier("sessionsRedisContainer") RedisMessageListenerContainer container) {
        this.mapper = mapper;
        this.sessionBroadcaster = sessionBroadcaster;
        this.container = container;
    }

    @PostConstruct
    public void subscribe() {
        container.addMessageListener(this, new PatternTopic("channelrack.*"));
        System.out.println("RackEventRedisListener escuchando Redis");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("=== RAW MESSAGE DEBUG ===");
        System.out.println("Body:    " + new String(message.getBody()));
        System.out.println("Channel: " + new String(message.getChannel()));
        System.out.println("Pattern: " + (pattern != null ? new String(pattern) : "null"));
        String payload = new String(message.getBody());
        Map<String, Object> eventData;

        try {
            eventData = mapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String correlationId = (String) eventData.get("correlationId");

        sessionBroadcaster.broadcast(new BroadcastInfo(correlationId,eventData));
    }

}
