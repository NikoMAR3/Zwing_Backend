package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.application.mappers.ChannelRackCommandMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.AddChannelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AddChannelHandler implements ChannelRackEventHandler {

    @Autowired
    AddChannelUseCase addChannelUseCase;

    @Override
    public void handle(String rackId, Map<String, Object> data, String correlationId) {
        addChannelUseCase.execute((ChannelRackCommand.AddChannel) ChannelRackCommandMapper.map("ADD_CHANNEL",rackId,data,correlationId));
    }
}
