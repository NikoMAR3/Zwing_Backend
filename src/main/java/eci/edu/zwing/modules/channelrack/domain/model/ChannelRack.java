package eci.edu.zwing.modules.channelrack.domain.model;


import eci.edu.zwing.modules.channelrack.domain.model.valueobjects.ChannelData;

import java.util.List;

public class ChannelRack extends EventSourcing {
    private String channelRackId;
    private List<Channel> channels;

    public void checkVersion(long expectedVersion) {
        if (this.version != expectedVersion) {
            throw new IllegalStateException(
                    "Version mismatch: expected " + expectedVersion + " but was " + this.version
            );
        }
    }

    public void addChannel(String channelId,ChannelData data, Long expectedVersion){
        checkVersion(expectedVersion);
        boolean exists = channels.stream()
                .anyMatch(c -> c.getChannelId().equals(channelId));
        if (!exists) {
            raise(new DomainEvent.ChannelAdded(
                    channelRackId,
                    channelId,
                    data.name(),
                    data.sampleId(),
                    data.volume(),
                    data.mute()
            ));
        }
    }

    public void removeChannel(String channelId,Long expectedVersion){
        checkChannelExistence(channelId);
        checkVersion(expectedVersion);
        raise(new DomainEvent.ChannelRemoved(
                channelRackId,
                channelId
        ));
    }

    public void activateStep(String channelId,int stepIndex,Long expectedVersion){
        checkChannelExistence(channelId);
        checkVersion(expectedVersion);
        raise(new DomainEvent.StepActivated(
                channelRackId,
                channelId,
                stepIndex
        ));
    }

    public void deactivateStep(String channelId,int stepIndex,Long expectedVersion){
        checkChannelExistence(channelId);
        checkVersion(expectedVersion);
        raise(new DomainEvent.StepDeactivated(
                channelRackId,
                channelId,
                stepIndex
        ));
    }

    public List<Channel> getChannels(){
        return channels;
    }

    private void checkChannelExistence(String channelId){
        boolean exists = channels.stream()
                .anyMatch(c -> c.getChannelId().equals(channelId));

        if (!exists) {
            throw new IllegalArgumentException("Channel does not exist: " + channelId);
        }
    }

    @Override
    void apply(DomainEvent.ChannelAdded event) {
        channels.add(new Channel(
           event.channelId(),
           event.name(),
           event.sampleId(),
           event.volume(),
           event.mute()
        ));
        version++;
    }

    @Override
    void apply(DomainEvent.ChannelRemoved event) {
        channels.removeIf(channel -> channel.getChannelId().equals(event.channelId()));
        version++;
    }

    @Override
    void apply(DomainEvent.StepActivated event) {
        channels.stream()
                .filter(channel -> channel.getChannelId().equals(event.channelId()))
                .forEach(channel -> channel.activateStep(event.stepIndex()));
        version++;
    }

    @Override
    void apply(DomainEvent.StepDeactivated event) {
        channels.stream()
                .filter(channel -> channel.getChannelId().equals(event.channelId()))
                .forEach(channel -> channel.deactivateStep(event.stepIndex()));
        version++;
    }
}
