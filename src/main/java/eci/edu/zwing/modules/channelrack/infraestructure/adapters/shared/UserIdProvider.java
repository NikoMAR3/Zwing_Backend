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
        // Primero intenta obtener del ThreadLocal (para Redis/WebSocket)
        String userId = userIdContext.get();
        if (userId != null && !userId.isEmpty()) {
            return userId;
        }

        // Si no está en ThreadLocal, intenta del header HTTP (para REST)
        if (request != null) {
            try {
                userId = request.getHeader("X-User-Id");
                if (userId != null && !userId.isEmpty()) {
                    return userId;
                }
            } catch (IllegalStateException e) {
                // En contexto de Redis, HttpServletRequest no está disponible
            }
        }

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