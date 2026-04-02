package eci.edu.zwing.modules.channelrack.domain.model;

public sealed interface DomainEvent {

    void applyTo(EventSourcing aggregate);

    String channelRackId();
    String channelId();

    record ChannelAdded(String channelRackId, String channelId, String name, String sampleId, float volume, boolean mute) implements DomainEvent {
        @Override public void applyTo(EventSourcing aggregate) {aggregate.apply(this);}
    }

    record ChannelRemoved(String channelRackId, String channelId) implements DomainEvent {
        @Override public void applyTo(EventSourcing aggregate) {aggregate.apply(this);}
    }
    record StepActivated(String channelRackId, String channelId, int stepIndex) implements DomainEvent {
        @Override public void applyTo(EventSourcing aggregate) {aggregate.apply(this);}
    }
    record StepDeactivated(String channelRackId, String channelId, int stepIndex) implements DomainEvent {
        @Override public void applyTo(EventSourcing aggregate) {aggregate.apply(this);}
    }

}
