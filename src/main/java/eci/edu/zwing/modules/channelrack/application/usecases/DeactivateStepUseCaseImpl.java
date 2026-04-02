package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.DeactivateStepUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;

public class DeactivateStepUseCaseImpl implements DeactivateStepUseCase {

    private final ChannelRackRepository repository;

    public DeactivateStepUseCaseImpl(ChannelRackRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(ChannelRackCommand.DeactivateStep deactivateStepCommand) {
        ChannelRack rack = repository.load(deactivateStepCommand.channelId());
        rack.activateStep(deactivateStepCommand.channelId(), deactivateStepCommand.stepIndex(),deactivateStepCommand.expectedVersion());
        repository.save(rack);
    }
}
