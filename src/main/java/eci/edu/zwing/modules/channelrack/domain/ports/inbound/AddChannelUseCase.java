package eci.edu.zwing.modules.channelrack.domain.ports.inbound;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;

public interface AddChannelUseCase {
    void execute(ChannelRackCommand.AddChannel addChannelCommand);
}
