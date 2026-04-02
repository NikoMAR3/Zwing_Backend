package eci.edu.zwing.modules.channelrack.model.domain;


import java.util.List;

public abstract class EventSourcing {
    protected Long version;
    protected List<DomainEvent> uncommittedEvents;

    void raise(DomainEvent event){
        event.applyTo(this);
        uncommittedEvents.add(event);
    }

    abstract void apply(DomainEvent.ChannelAdded event);
    abstract void apply(DomainEvent.ChannelRemoved event);
    abstract void apply(DomainEvent.StepActivated event);
    abstract void apply(DomainEvent.StepDeactivated event);
}
