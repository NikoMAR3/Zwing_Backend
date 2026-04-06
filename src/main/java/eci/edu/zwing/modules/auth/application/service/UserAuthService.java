package eci.edu.zwing.modules.auth.application.service;


import eci.edu.zwing.modules.auth.UserDeletedEvent;
import eci.edu.zwing.modules.auth.application.port.in.AuthenticateWithGoogleUseCase;
import eci.edu.zwing.modules.auth.application.port.in.LogoutUseCase;
import eci.edu.zwing.modules.auth.application.port.in.UserDeleteCase;
import eci.edu.zwing.modules.auth.application.port.out.GoogleAuthPort;
import eci.edu.zwing.modules.auth.application.port.out.TokenBlacklistPort;
import eci.edu.zwing.modules.auth.application.port.out.TokenGeneratorPort;
import eci.edu.zwing.modules.auth.application.port.out.UserRepositoryAuthOutPort;
import eci.edu.zwing.modules.auth.domain.model.GoogleUserData;
import eci.edu.zwing.modules.auth.domain.model.User;
import eci.edu.zwing.modules.auth.infraestructure.web.dto.response.AuthResponse;
import lombok.AllArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserAuthService implements AuthenticateWithGoogleUseCase, UserDeleteCase, LogoutUseCase {
    private final GoogleAuthPort googleAuthAdapter;
    private final UserRepositoryAuthOutPort userRepository;
    private final TokenGeneratorPort tokenGeneratorPort;
    private ApplicationEventPublisher eventPublisher;
    private final TokenBlacklistPort tokenBlacklistPort;

    @Override
    @Transactional
    public AuthResponse authenticate(String idToken) {
        GoogleUserData googleUser = googleAuthAdapter.verifyToken(idToken);

        Optional<User> optionalUser = userRepository.findByEmail(googleUser.getEmail());
        User user;
        boolean isNewUser = false;

        if (optionalUser.isEmpty()) {
            user = new User(UUID.randomUUID(), googleUser.getName(), googleUser.getEmail());
            user = userRepository.save(user);
            isNewUser = true;
        } else {
            user = optionalUser.get();
        }
        String internalToken = tokenGeneratorPort.generateToken(user);

        return new AuthResponse(internalToken, user.getName(), user.getEmail(), isNewUser);
    }


    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);

        eventPublisher.publishEvent(new UserDeletedEvent(userId));
    }

    @Override
    public void logout(String token) {
        tokenBlacklistPort.blacklistToken(token, 86400);
    }
}
