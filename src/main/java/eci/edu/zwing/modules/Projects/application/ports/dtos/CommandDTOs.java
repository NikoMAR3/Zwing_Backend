package eci.edu.zwing.modules.Projects.application.ports.dtos;

public sealed interface CommandDTOs {
    record GetProjectCommandDTO(String projectId) implements CommandDTOs{}

    record GetUserProjectsDTO(String userId) implements CommandDTOs{}

    record RemoveProjectCommandDTO(String projectId) implements CommandDTOs{}

    record AddProjectMemberCommandDTO(String projectId, String userId, ProjectRole role)implements CommandDTOs {}

    record RemoveProjectMemberCommandDTO(String projectId, String userId) implements CommandDTOs{}

    record SetProjectMemberRoleUseCaseDTO(String projectId, String userId, ProjectRole role) implements CommandDTOs{}

    record AddSnapshotRefUseCaseDTO(String projectId, String snapshotId, DateTime takenAt, WorkplaceType type) implements CommandDTOs{}

}
