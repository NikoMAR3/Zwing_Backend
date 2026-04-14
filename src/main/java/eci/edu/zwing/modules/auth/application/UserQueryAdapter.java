
package eci.edu.zwing.modules.auth.application;


import eci.edu.zwing.modules.auth.application.port.out.UserRepositoryAuthOutPort;
import eci.edu.zwing.modules.auth.infraestructure.adapters.in.UserQueryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserQueryAdapter implements UserQueryPort {

    private final UserRepositoryAuthOutPort userRepository;

    @Override
    public Optional<UserData> findById(UUID userId) {
        return userRepository.findById(userId)
                .map(u -> new UserData(u.getUserId(), u.getName(), u.getEmail()));
    }

    @Override
    public Optional<UserData> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(u -> new UserData(u.getUserId(), u.getName(), u.getEmail()));
    }

    @Override
    public List<UserData> findByIds(List<UUID> userIds) {
        return userIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(u -> new UserData(u.getUserId(), u.getName(), u.getEmail()))
                .toList();
    }
}