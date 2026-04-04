package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void subscribe() {
        container.addMessageListener(this, new PatternTopic("realtime:events:channelrack"));
        System.out.println("ChannelRackRedisEventListener escuchando Redis");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String payload = new String(message.getBody());
            Map<String, Object> eventData = mapper.readValue(payload, Map.class);

            String rackId = (String) eventData.get("sessionId");
            String action = (String) eventData.get("action");
            Map<String, Object> data = (Map<String, Object>) eventData.get("data");


            // Procesar según la acción
            switch (action) {
                case "addChannel":
                    handleAddChannel(rackId, data);
                    break;

                case "removeChannel":
                    handleRemoveChannel(rackId, data);
                    break;

                case "activateStep":
                    handleActivateStep(rackId, data);
                    break;

                case "deactivateStep":
                    handleDeactivateStep(rackId, data);
                    break;
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleAddChannel(String rackId, Map<String, Object> data) {
        String channelID = (String) data.get("channelID");
        String name = (String) data.get("name");
        String sampleId = (String) data.get("sampleId");
        Long expectedVersion = ((Number) data.get("expectedVersion")).longValue();

        ChannelRackCommand.AddChannel command = new ChannelRackCommand.AddChannel(
                rackId, channelID, name, sampleId, expectedVersion
        );
        addChannelUseCase.execute(command);
        System.out.println("Canal agregado");
    }

    private void handleRemoveChannel(String rackId, Map<String, Object> data) {
        String channelId = (String) data.get("channelId");
        Long expectedVersion = ((Number) data.get("expectedVersion")).longValue();

        ChannelRackCommand.RemoveChannel command =
                new ChannelRackCommand.RemoveChannel(rackId, channelId, expectedVersion);

        removeChannelUseCase.execute(command);
        System.out.println("✓ Canal removido");
    }

    private void handleActivateStep(String rackId, Map<String, Object> data) {
        String channelId = (String) data.get("channelId");
        int stepIndex = ((Number) data.get("stepIndex")).intValue();
        Long expectedVersion = ((Number) data.get("expectedVersion")).longValue();

        ChannelRackCommand.ActivateStep command =
                new ChannelRackCommand.ActivateStep(rackId, channelId, stepIndex, expectedVersion);

        activateStepUseCase.execute(command);
        System.out.println("✓ Step activado");
    }

    private void handleDeactivateStep(String rackId, Map<String, Object> data) {
        String channelId = (String) data.get("channelId");
        int stepIndex = ((Number) data.get("stepIndex")).intValue();
        Long expectedVersion = ((Number) data.get("expectedVersion")).longValue();

        ChannelRackCommand.DeactivateStep command =
                new ChannelRackCommand.DeactivateStep(rackId, channelId, stepIndex, expectedVersion);

        deactivateStepUseCase.execute(command);
        System.out.println("✓ Step desactivado");
    }

}