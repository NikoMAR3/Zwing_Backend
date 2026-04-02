package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.ActivateStepUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;

public class ActivateStepUseCaseImpl implements ActivateStepUseCase {

    private final ChannelRackRepository repository;

    public ActivateStepUseCaseImpl(ChannelRackRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ChannelRackCommand.ActivateStep activateStepCommand) {
        ChannelRack rack = repository.load(activateStepCommand.channelId());
        rack.activateStep(activateStepCommand.channelId(), activateStepCommand.stepIndex(),activateStepCommand.expectedVersion());
        repository.save(rack);
    }
}
