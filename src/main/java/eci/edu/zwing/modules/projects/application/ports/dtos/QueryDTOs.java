package eci.edu.zwing.modules.projects.application.ports.dtos;

import java.util.UUID;

public sealed interface QueryDTOs {
    record UserData(UUID userId, String email, String name, String picture) implements  QueryDTOs{}
}
