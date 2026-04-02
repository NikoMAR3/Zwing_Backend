package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.model.valueobjects.ChannelData;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.AddChannelUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;

public class AddChannelUseCaseImpl implements AddChannelUseCase {

    private final ChannelRackRepository repository;

    public AddChannelUseCaseImpl(ChannelRackRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ChannelRackCommand.AddChannel addChannelCommand) {
        ChannelRack rack = repository.load(addChannelCommand.rackId());
        rack.addChannel(
                addChannelCommand.channelID(),
                new ChannelData(
                        addChannelCommand.name(),
                        addChannelCommand.sampleId(),
                        100,
                        false
                ),
                addChannelCommand.expectedVersion()
        );
    }
}
