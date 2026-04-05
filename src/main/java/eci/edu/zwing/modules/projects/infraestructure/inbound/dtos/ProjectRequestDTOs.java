package eci.edu.zwing.modules.projects.infraestructure.inbound.dtos;

public sealed interface ProjectRequestDTOs {

    record AddMemberRequest(String userId, String role) implements ProjectRequestDTOs{}

    record SetMemberRoleRequest(String role)  implements ProjectRequestDTOs{}

    record AddSnapshotRequest(String toolRefId, String takenAt, String type)  implements ProjectRequestDTOs {}

    record CreateProjectRequest(String name, String ownerId)  implements ProjectRequestDTOs{}

}
