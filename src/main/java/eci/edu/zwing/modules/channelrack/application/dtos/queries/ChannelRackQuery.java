package eci.edu.zwing.modules.channelrack.application.dtos.queries;

public sealed interface ChannelRackQuery {
    record GetChannelById(String rackId, String channelId) implements ChannelRackQuery {}
    record ListChannelsInRack(String rackId, int limit, int offset) implements ChannelRackQuery {}
}
