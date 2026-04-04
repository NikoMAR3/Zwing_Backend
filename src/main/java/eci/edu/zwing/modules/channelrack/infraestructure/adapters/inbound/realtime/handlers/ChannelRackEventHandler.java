package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound.realtime.handlers;

import java.util.Map;

public interface ChannelRackEventHandler {
    void handle(String rackId, Map<String, Object> data);
}
