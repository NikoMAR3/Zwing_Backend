package eci.edu.zwing.modules.channelrack.infraestructure.adapters.shared;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserIdProvider {

    private static final ThreadLocal<String> userIdContext = new ThreadLocal<>();
    private final HttpServletRequest request;

    public UserIdProvider(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Obtiene el userId desde ThreadLocal (Redis/WebSocket) o desde el header HTTP (REST)
     */
    public String getCurrentUserId() {

        String userId = userIdContext.get();

        if (userId != null && !userId.isEmpty()) {
            System.out.println("Returning from ThreadLocal: " + userId);
            return userId;
        }

        if (request != null) {
            try {
                String headerUserId = request.getHeader("X-User-Id");
                System.out.println("Header X-User-Id: " + headerUserId);
                if (headerUserId != null && !headerUserId.isEmpty()) {
                    System.out.println("Returning from Header: " + headerUserId);
                    return headerUserId;
                }
            } catch (IllegalStateException e) {
                System.out.println("HttpServletRequest not available");
            }
        }

        System.out.println("Throwing exception - no userId found");
        throw new IllegalStateException("User ID not found in request, WebSocket or Redis context");
    }

    /**
     * Establece el userId en el contexto thread-local (para Redis/WebSocket)
     */
    public void setCurrentUserId(String userId) {
        userIdContext.set(userId);
    }

    /**
     * Limpia el userId del contexto thread-local
     */
    public void clearCurrentUserId() {
        userIdContext.remove();
    }

}