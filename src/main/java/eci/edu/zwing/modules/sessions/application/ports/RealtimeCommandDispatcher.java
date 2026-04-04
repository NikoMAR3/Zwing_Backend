package eci.edu.zwing.modules.sessions.application.ports;

import eci.edu.zwing.modules.sessions.infrastructure.RealtimeCommand;

public interface RealtimeCommandDispatcher {
    void dispatch(String sessionId, RealtimeCommand command) throws Exception;
}
