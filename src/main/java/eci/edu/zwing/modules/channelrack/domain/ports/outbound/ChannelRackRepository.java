package eci.edu.zwing.modules.channelrack.domain.ports.outbound;

import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;

public interface ChannelRackRepository {
    public void save(ChannelRack channelRack,String correlationId);
    public ChannelRack load(String channelRackId);
}
