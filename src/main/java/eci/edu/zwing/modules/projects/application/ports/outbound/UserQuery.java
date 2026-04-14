package eci.edu.zwing.modules.projects.application.ports.outbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.QueryDTOs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserQuery {
    Optional<QueryDTOs.UserData> getUserById(UUID userId);
    Optional<QueryDTOs.UserData> getUserByEmail(String email);
    List<QueryDTOs.UserData> getUsersByIds(List<UUID> userIds);
}
