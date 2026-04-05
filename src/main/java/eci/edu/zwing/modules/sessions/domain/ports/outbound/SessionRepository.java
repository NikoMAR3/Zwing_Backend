package eci.edu.zwing.modules.sessions.domain.ports.outbound;


import eci.edu.zwing.modules.sessions.domain.Session;
import eci.edu.zwing.modules.sessions.domain.SessionId;
import eci.edu.zwing.modules.sessions.domain.ToolId;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para persistencia de sesiones (contrato de dominio)
 */
public interface SessionRepository {

    void save(Session session);

    void update(Session session);

    Optional<Session> findById(SessionId id);

    List<Session> findByUserId(String userId);

    List<Session> findByToolId(ToolId toolId);

    List<Session> findActiveByToolId(ToolId toolId);

    List<Session> findAll();

    void delete(SessionId id);
}