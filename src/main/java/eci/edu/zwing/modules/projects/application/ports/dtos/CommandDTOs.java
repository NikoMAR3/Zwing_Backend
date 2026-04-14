package eci.edu.zwing.modules.projects.application.ports.dtos;

import eci.edu.zwing.modules.projects.domain.model.ProjectRole;
import eci.edu.zwing.modules.projects.domain.model.WorkplaceType;

public sealed interface CommandDTOs {
    record GetProjectCommandDTO(String projectId) implements CommandDTOs{}

    record GetUserProjectsDTO(String userId) implements CommandDTOs{}

    record RemoveProjectCommandDTO(String projectId) implements CommandDTOs{}

    record AddProjectMemberCommandDTO(String projectId, String userId, String role)implements CommandDTOs {}

    record RemoveProjectMemberCommandDTO(String projectId, String userId) implements CommandDTOs{}

    record SetProjectMemberRoleUseCaseDTO(String projectId, String userId, String role) implements CommandDTOs{}

    record AddToolRefUseCaseDTO(String projectId, String toolRefId, String type) implements CommandDTOs{}

    record CreateProjectCommandDTO(String name, String ownerId) {}

}
