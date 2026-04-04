package eci.edu.zwing.modules.sessions.application.ports;

import eci.edu.zwing.modules.sessions.domain.RealtimeEvent;

public interface RealtimeEventPublisher {
    void publishEvent(RealtimeEvent event) throws Exception;
}
