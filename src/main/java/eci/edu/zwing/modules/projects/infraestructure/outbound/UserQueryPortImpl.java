package eci.edu.zwing.modules.projects.infraestructure.outbound;

import eci.edu.zwing.modules.auth.infraestructure.adapters.in.UserQueryPort;
import eci.edu.zwing.modules.projects.application.ports.dtos.QueryDTOs;
import eci.edu.zwing.modules.projects.application.ports.outbound.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserQueryPortImpl implements UserQuery {

    private final UserQueryPort userQueryPort;

    @Autowired
    public UserQueryPortImpl(UserQueryPort userQueryPort) {
        this.userQueryPort = userQueryPort;
    }

    @Override
    public Optional<QueryDTOs.UserData> getUserById(UUID userId) {
        return userQueryPort.findById(userId)
                .map(u -> new QueryDTOs.UserData(u.userId(), u.email(), u.name()));
    }

    @Override
    public Optional<QueryDTOs.UserData> getUserByEmail(String email) {
        return userQueryPort.findByEmail(email)
                .map(u -> new QueryDTOs.UserData(u.userId(), u.email(), u.name()));
    }

    @Override
    public List<QueryDTOs.UserData> getUsersByIds(List<UUID> userIds) {
        return userQueryPort.findByIds(userIds).stream()
                .map(u -> new QueryDTOs.UserData(u.userId(), u.email(), u.name()))
                .toList();
    }
}
