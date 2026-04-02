package eci.edu.zwing.modules.channelrack.domain.ports.inbound;

import eci.edu.zwing.modules.channelrack.application.dtos.queries.ChannelRackQuery;
import eci.edu.zwing.modules.channelrack.application.dtos.responses.ChannelRackResponse;


public interface GetChannelsUseCase {
     ChannelRackResponse execute(ChannelRackQuery.ListChannelsInRack channelRackQuery);
}
