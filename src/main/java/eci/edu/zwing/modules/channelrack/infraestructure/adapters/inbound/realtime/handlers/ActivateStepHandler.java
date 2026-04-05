package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.application.mappers.ChannelRackCommandMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.ActivateStepUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActivateStepHandler implements ChannelRackEventHandler{

    @Autowired
    ActivateStepUseCase activateStepUseCase;

    @Override
    public void handle(String rackId, Map<String, Object> data, String correlationId) {
        activateStepUseCase.execute((ChannelRackCommand.ActivateStep) ChannelRackCommandMapper.map("ACTIVATE_STEP",rackId,data,correlationId));
    }
}
