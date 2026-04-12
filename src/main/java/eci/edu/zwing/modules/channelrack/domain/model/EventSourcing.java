package eci.edu.zwing.modules.channelrack.domain.model;


import java.util.ArrayList;
import java.util.List;

public abstract class EventSourcing {
    protected Long version;
    protected List<DomainEvent> uncommittedEvents = new ArrayList<>();

    public Long getVersion() {
        return version;
    }

    public List<DomainEvent> getUncommittedEvents() {
        return uncommittedEvents;
    }

    void raise(DomainEvent event){
        event.applyTo(this);
        uncommittedEvents.add(event);
    }

    public void applyEvent(DomainEvent event) {
        event.applyTo(this);
    }

    abstract void apply(DomainEvent.ChannelAdded event);
    abstract void apply(DomainEvent.ChannelRemoved event);
    abstract void apply(DomainEvent.StepActivated event);
    abstract void apply(DomainEvent.StepDeactivated event);
    abstract void apply(DomainEvent.ChannelRackCreated event);
    public void markEventsAsCommitted() {
        uncommittedEvents.clear();
    }
}
