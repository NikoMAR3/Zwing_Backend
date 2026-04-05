package eci.edu.zwing.modules.sessions.domain;


/**
 * Enum que representa el estado de una sesión
 */
public enum SessionStatus {
    ACTIVE("Activa"),
    PAUSED("Pausada"),
    CLOSED("Cerrada"),
    EXPIRED("Expirada");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
