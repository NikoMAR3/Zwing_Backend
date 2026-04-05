package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.CreateChannelRackUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateChannelRackUseCaseImpl implements CreateChannelRackUseCase {

    @Autowired
    ChannelRackRepository repository;

    @Override
    public ChannelRack execute() {
        ChannelRack rack = ChannelRack.create();
        repository.save(rack,null);
        return rack;
    }

}
