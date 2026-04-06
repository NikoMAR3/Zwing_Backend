package eci.edu.zwing.modules.auth;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserDeletedEvent {
    private final UUID userId;
    public UserDeletedEvent(UUID userId) {
        this.userId = userId;
    }
}