package eci.edu.zwing.modules.auth.application.port.out;

import eci.edu.zwing.modules.auth.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryAuthOutPort {
    User save(User user);
    void delete(User user);
    Optional<User> findById(UUID id);
    User update (UUID id, User user);
    Optional<User> findByEmail(String email);
}
