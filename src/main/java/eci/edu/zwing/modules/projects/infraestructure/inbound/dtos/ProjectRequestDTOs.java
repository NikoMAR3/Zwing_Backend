package eci.edu.zwing.modules.projects.infraestructure.inbound.dtos;

public sealed interface ProjectRequestDTOs {

    record AddMemberRequest(String userId, String role) {}

    record SetMemberRoleRequest(String role) {}

    record AddSnapshotRequest(String toolRefId, String takenAt, String type) {}

    record CreateProjectRequest(String name, String ownerId) {}

}
