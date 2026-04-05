package eci.edu.zwing.modules.sessions.domain;


import java.util.Objects;
import java.util.UUID;

/**
 * Value Object que representa el identificador único de una sesión
 */
public class SessionId {

    private final String value;

    private SessionId(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static SessionId generate() {
        return new SessionId(UUID.randomUUID().toString());
    }

    public static SessionId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("SessionId no puede estar vacío");
        }
        return new SessionId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId = (SessionId) o;
        return Objects.equals(value, sessionId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
