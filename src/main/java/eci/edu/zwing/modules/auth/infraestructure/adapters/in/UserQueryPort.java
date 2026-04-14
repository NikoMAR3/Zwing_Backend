package eci.edu.zwing.modules.auth.infraestructure.adapters.in;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserQueryPort {
    Optional<UserData> findById(UUID userId);
    Optional<UserData> findByEmail(String email);
    List<UserData> findByIds(List<UUID> userIds);

    record UserData(UUID userId, String name, String email) {}
}
