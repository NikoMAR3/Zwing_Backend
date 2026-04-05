package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChannelRackHandlerRegistry {

    private final Map<String, ChannelRackEventHandler> handlers;

    public ChannelRackHandlerRegistry(
            AddChannelHandler addChannel,
            RemoveChannelHandler removeChannel,
            ActivateStepHandler activateStep,
            DeactivateStepHandler deactivateStep
    ) {
        this.handlers = Map.of(
                "addChannel",    addChannel,
                "removeChannel", removeChannel,
                "activateStep",  activateStep,
                "deactivateStep", deactivateStep
        );
    }

    public ChannelRackEventHandler get(String action) {
        ChannelRackEventHandler handler = handlers.get(action);
        if (handler == null) throw new IllegalArgumentException("Unknown action: " + action);
        return handler;
    }
}