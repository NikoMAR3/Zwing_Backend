package eci.edu.zwing.modules.projects.application.ports.dtos;

import eci.edu.zwing.modules.projects.domain.model.ProjectRole;
import eci.edu.zwing.modules.projects.domain.model.WorkplaceType;

public sealed interface CommandDTOs {
    record GetProjectCommandDTO(String projectId) implements CommandDTOs{}

    record GetUserProjectsDTO(String userId) implements CommandDTOs{}

    record RemoveProjectCommandDTO(String projectId) implements CommandDTOs{}

    record AddProjectMemberCommandDTO(String projectId, String userId, ProjectRole role)implements CommandDTOs {}

    record RemoveProjectMemberCommandDTO(String projectId, String userId) implements CommandDTOs{}

    record SetProjectMemberRoleUseCaseDTO(String projectId, String userId, ProjectRole role) implements CommandDTOs{}

    record AddSnapshotRefUseCaseDTO(String projectId, String toolRefId, WorkplaceType type) implements CommandDTOs{}

    record CreateProjectCommandDTO(String name, String ownerId) {}

}
