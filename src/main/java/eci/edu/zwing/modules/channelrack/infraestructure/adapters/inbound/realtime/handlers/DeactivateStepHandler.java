package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.application.mappers.ChannelRackCommandMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.DeactivateStepUseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class DeactivateStepHandler implements ChannelRackEventHandler {

    @Autowired
    DeactivateStepUseCase deactivateStepUseCase;

    @Override
    public void handle(String rackId, Map<String, Object> data, String correlationId) {
        deactivateStepUseCase.execute((ChannelRackCommand.DeactivateStep) ChannelRackCommandMapper.map("DEACTIVATE_STEP",rackId,data,correlationId));
    }

}
