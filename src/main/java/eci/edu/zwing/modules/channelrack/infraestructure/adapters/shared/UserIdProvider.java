package eci.edu.zwing.modules.channelrack.infraestructure.adapters.shared;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;


@Component
public class UserIdProvider {

    private final HttpServletRequest request;

    public UserIdProvider(HttpServletRequest request) {
        this.request = request;
    }

    public String getCurrentUserId() {
        String userId = request.getHeader("X-User-Id");

        if (userId == null || userId.isEmpty()) {
            throw new IllegalStateException("User ID not found in request header");
        }

        return userId;
    }

    public boolean isAuthenticated() {
        return request.getHeader("X-User-Id") != null;
    }
}
