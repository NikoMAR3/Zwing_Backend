package eci.edu.zwing.modules.projects.infraestructure.outbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.QueryDTOs;
import eci.edu.zwing.modules.projects.application.ports.outbound.UserQueryInPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserQueryPortImpl implements UserQueryInPort {
    @Override
    public Optional<QueryDTOs.UserData> getUserById(UUID userId) {
        return Optional.empty();
    }

    @Override
    public Optional<QueryDTOs.UserData> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<QueryDTOs.UserData> getUsersByIds(List<UUID> userIds) {
        return List.of();
    }
}
