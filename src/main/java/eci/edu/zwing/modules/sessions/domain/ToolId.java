package eci.edu.zwing.modules.sessions.domain;

import java.util.Objects;

/**
 * Value Object que representa el identificador de una herramienta
 */
public class ToolId {

    private final String value;

    private ToolId(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ToolId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ToolId no puede estar vacío");
        }
        return new ToolId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolId toolId = (ToolId) o;
        return Objects.equals(value, toolId.value);
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
