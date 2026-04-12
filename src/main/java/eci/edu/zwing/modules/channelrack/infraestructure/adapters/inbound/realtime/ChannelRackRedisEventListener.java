package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers.ChannelRackEventHandler;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers.ChannelRackHandlerRegistry;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.shared.UserIdProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Component
public class ChannelRackRedisEventListener implements MessageListener {

    @Autowired
    @Qualifier("channelRackRedisContainer")
    private RedisMessageListenerContainer container;

    @Autowired
    private AddChannelUseCase addChannelUseCase;

    @Autowired
    private RemoveChannelUseCase removeChannelUseCase;

    @Autowired
    private ActivateStepUseCase activateStepUseCase;

    @Autowired
    private DeactivateStepUseCase deactivateStepUseCase;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ChannelRackHandlerRegistry ChannelRackHandlerRegistry;

    @Autowired
    private UserIdProvider userIdProvider;

    private static final ObjectMapper mapper = new ObjectMapper();


    @PostConstruct
    public void subscribe() {
        container.addMessageListener(this, new PatternTopic("realtime:events:channelrack"));
        System.out.println("ChannelRackRedisEventListener escuchando Redis");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.print("llego algo");
        String payload = new String(message.getBody());
        Map<String, Object> eventData;

        try {
            eventData = mapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> data = (Map<String, Object>) eventData.get("data");

        userIdProvider.setCurrentUserId((String) eventData.get("userId"));

        ChannelRackEventHandler channelRackEventHandler = ChannelRackHandlerRegistry.get((String) eventData.get("action"));
        channelRackEventHandler.handle((String) data.get("rackId"), data, (String) eventData.get("sessionId"));

        userIdProvider.clearCurrentUserId();
    }

}