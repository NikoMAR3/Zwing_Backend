package eci.edu.zwing.modules.projects.infraestructure.inbound;


import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.ProjectRole;
import eci.edu.zwing.modules.projects.domain.model.WorkplaceType;
import eci.edu.zwing.modules.projects.infraestructure.inbound.dtos.ProjectRequestDTOs;

public class ProjectDTOMapper {

    /**
     * Convierte AddMemberRequest a AddProjectMemberCommandDTO
     */
    public static CommandDTOs.AddProjectMemberCommandDTO mapToAddProjectMemberCommand(
            String projectId,
            ProjectRequestDTOs.AddMemberRequest request) {

        ProjectRole role = parseProjectRole(request.role());
        return new CommandDTOs.AddProjectMemberCommandDTO(projectId, request.userId(), role);
    }

    /**
     * Convierte SetMemberRoleRequest a SetProjectMemberRoleUseCaseDTO
     */
    public static CommandDTOs.SetProjectMemberRoleUseCaseDTO mapToSetProjectMemberRole(
            String projectId,
            String userId,
            ProjectRequestDTOs.SetMemberRoleRequest request) {

        ProjectRole role = parseProjectRole(request.role());
        return new CommandDTOs.SetProjectMemberRoleUseCaseDTO(projectId, userId, role);
    }

    /**
     * Convierte AddSnapshotRequest a AddSnapshotRefUseCaseDTO
     */
    public static CommandDTOs.AddSnapshotRefUseCaseDTO mapToAddSnapshotRef(
            String projectId,
            ProjectRequestDTOs.AddSnapshotRequest request) {

        WorkplaceType type = parseWorkplaceType(request.type());
        return new CommandDTOs.AddSnapshotRefUseCaseDTO(projectId, request.toolRefId(), type);
    }

    /**
     * Convierte CreateProjectRequest a CreateProjectCommandDTO
     */
    public static CommandDTOs.CreateProjectCommandDTO mapToCreateProjectCommand(
            ProjectRequestDTOs.CreateProjectRequest request) {

        return new CommandDTOs.CreateProjectCommandDTO(request.name(), request.ownerId());
    }

    /**
     * Parsea un String a ProjectRole
     */
    private static ProjectRole parseProjectRole(String role) {
        try {
            return ProjectRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol de proyecto inválido: " + role, e);
        }
    }

    /**
     * Parsea un String a WorkplaceType
     */
    private static WorkplaceType parseWorkplaceType(String type) {
        try {
            return WorkplaceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de workspace inválido: " + type, e);
        }
    }
}