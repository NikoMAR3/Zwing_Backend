package eci.edu.zwing.modules.channelrack.application.usecases;

import eci.edu.zwing.modules.channelrack.application.dtos.queries.ChannelRackQuery;
import eci.edu.zwing.modules.channelrack.application.dtos.responses.ChannelRackResponse;
import eci.edu.zwing.modules.channelrack.application.mappers.ChannelMapper;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.GetChannelsUseCase;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.ChannelRackRepository;

import java.util.stream.Collectors;

public class GetChannelsUseCaseImpl implements GetChannelsUseCase {

    private final ChannelRackRepository repository;

    public GetChannelsUseCaseImpl(ChannelRackRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChannelRackResponse execute(ChannelRackQuery.ListChannelsInRack channelRackQuery) {
        ChannelRack rack = repository.load(channelRackQuery.rackId());
        return new ChannelRackResponse.RackResponse(
                channelRackQuery.rackId(),
                rack.getChannels().stream().map(ChannelMapper::toResponse).collect(Collectors.toList()),
                rack.getVersion()
        );

    }
}
