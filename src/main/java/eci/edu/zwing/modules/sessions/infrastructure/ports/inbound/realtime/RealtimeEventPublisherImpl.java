package eci.edu.zwing.modules.sessions.infrastructure.ports.inbound.realtime;

import eci.edu.zwing.modules.sessions.domain.RealtimeEvent;
import eci.edu.zwing.modules.sessions.application.ports.outbound.RealtimeEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class RealtimeEventPublisherImpl implements RealtimeEventPublisher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void publishEvent(RealtimeEvent event) throws Exception {
        String channel = "realtime:events:" + event.getEntityType();

        String payload = mapper.writeValueAsString(
                Map.of(
                        "entityType", event.getEntityType(),
                        "sessionId", event.getSessionId(),
                        "action", event.getAction(),
                        "data", event.getData(),
                        "timestamp", System.currentTimeMillis(),
                        "userId", event.getUserId()
                        )
        );
        redisTemplate.convertAndSend(channel, payload);
        System.out.println("📤 Evento publicado en Redis: " + channel);
    }

}