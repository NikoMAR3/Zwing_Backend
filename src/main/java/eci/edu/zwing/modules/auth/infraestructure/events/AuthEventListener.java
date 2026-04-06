package eci.edu.zwing.modules.auth.infraestructure.events;

import eci.edu.zwing.modules.auth.UserDeletedEvent;
import eci.edu.zwing.modules.auth.infraestructure.security.config.TokenBlacklistService;
import lombok.AllArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthEventListener {

    private final TokenBlacklistService tokenBlacklistService;

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        tokenBlacklistService.blacklistUser(event.getUserId().toString(), 86400);
    }
}
