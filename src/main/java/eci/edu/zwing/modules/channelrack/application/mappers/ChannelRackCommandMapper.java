package eci.edu.zwing.modules.channelrack.application.mappers;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;

import java.util.Map;

public class ChannelRackCommandMapper {

    public static ChannelRackCommand map(String action, String rackId, Map<String, Object> data,String correlationId) {
        return switch (action) {
            case "ADD_CHANNEL" -> new ChannelRackCommand.AddChannel(
                    rackId,
                    (String) data.get("channelId"),
                    (String) data.get("name"),
                    (String) data.get("sampleId"),
                    (Long) data.get("expectedVersion"),
                    correlationId
            );
            case "REMOVE_CHANNEL" -> new ChannelRackCommand.RemoveChannel(
                    rackId,
                    (String) data.get("channelId"),
                    (Long) data.get("expectedVersion"),
                    correlationId
            );
            case "ACTIVATE_STEP" -> new ChannelRackCommand.ActivateStep(
                    rackId,
                    (String) data.get("channelId"),
                    (int) data.get("stepIndex"),
                    (Long) data.get("expectedVersion"),
                    correlationId
            );
            case "DEACTIVATE_STEP" -> new ChannelRackCommand.DeactivateStep(
                    rackId,
                    (String) data.get("channelId"),
                    (int) data.get("stepIndex"),
                    (Long) data.get("expectedVersion"),
                    correlationId
            );
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        };
    }
}