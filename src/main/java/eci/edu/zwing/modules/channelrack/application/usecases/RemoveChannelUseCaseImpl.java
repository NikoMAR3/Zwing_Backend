package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.RemoveChannelUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;

public class RemoveChannelUseCaseImpl implements RemoveChannelUseCase {

    private final ChannelRackRepository repository;

    public RemoveChannelUseCaseImpl(ChannelRackRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ChannelRackCommand.RemoveChannel removeChannelCommand) {
        ChannelRack  rack = repository.load(removeChannelCommand.rackId());
        rack.removeChannel(removeChannelCommand.channelId(), removeChannelCommand.expectedVersion());
        repository.save(rack);
    }
}
