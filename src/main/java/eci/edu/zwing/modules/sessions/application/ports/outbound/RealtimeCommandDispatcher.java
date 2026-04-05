package eci.edu.zwing.modules.sessions.application.ports.outbound;

import eci.edu.zwing.modules.sessions.infrastructure.realtime.RealtimeCommand;

public interface RealtimeCommandDispatcher {
    void dispatch(String sessionId, RealtimeCommand command) throws Exception;
}
