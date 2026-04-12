package eci.edu.zwing.modules.sessions.application.ports.outbound;

import eci.edu.zwing.modules.sessions.infrastructure.ports.redis.BroadcastInfo;

import java.util.Map;

public interface SessionBroadcaster {
    void broadcast(BroadcastInfo info);
}
