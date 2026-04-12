package eci.edu.zwing.modules.channelrack.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomainEvent.ChannelAdded.class,       name = "ChannelAdded"),
        @JsonSubTypes.Type(value = DomainEvent.ChannelRemoved.class,     name = "ChannelRemoved"),
        @JsonSubTypes.Type(value = DomainEvent.StepActivated.class,      name = "StepActivated"),
        @JsonSubTypes.Type(value = DomainEvent.StepDeactivated.class,    name = "StepDeactivated"),
        @JsonSubTypes.Type(value = DomainEvent.ChannelRackCreated.class, name = "ChannelRackCreated")
})
public sealed interface DomainEvent {

    void applyTo(EventSourcing aggregate);

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
    record ChannelRackCreated(String rackId) implements DomainEvent {
        @Override
        public void applyTo(EventSourcing aggregate) {aggregate.apply(this);}
    }
}
