package eci.edu.zwing.modules.channelrack.application.dtos.commands;

public sealed interface ChannelRackCommand {
    record AddChannel(String rackId, String channelID, String name, String sampleId,Long expectedVersion) implements ChannelRackCommand {}
    record RemoveChannel(String rackId, String channelId,Long expectedVersion) implements ChannelRackCommand {}
    record ActivateStep(String rackId, String channelId, int stepIndex,Long expectedVersion) implements ChannelRackCommand {}
    record DeactivateStep(String rackId, String channelId, int stepIndex,Long expectedVersion) implements ChannelRackCommand {}
}
