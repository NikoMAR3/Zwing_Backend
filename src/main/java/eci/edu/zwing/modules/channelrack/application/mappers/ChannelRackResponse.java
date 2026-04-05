package eci.edu.zwing.modules.channelrack.application.mappers;

import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;

public record ChannelRackResponse(String id) {
    public static ChannelRackResponse from(ChannelRack channelRack) {
        return new ChannelRackResponse(channelRack.getChannelRackId());
    }
}