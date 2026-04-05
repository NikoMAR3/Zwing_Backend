package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.application.mappers.ChannelRackCommandMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.RemoveChannelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RemoveChannelHandler implements ChannelRackEventHandler {

    @Autowired
    RemoveChannelUseCase removeChannelUseCase;

    @Override
    public void handle(String rackId, Map<String, Object> data, String correlationId) {
        removeChannelUseCase.execute((ChannelRackCommand.RemoveChannel) ChannelRackCommandMapper.map("REMOVE_CHANNEL",rackId,data,correlationId));
    }
}
