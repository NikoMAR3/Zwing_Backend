package eci.edu.zwing.modules.projects.domain.model;

import lombok.Getter;
import lombok.Setter;


public class ProjectMember {

    private final String userId;
    private ProjectRole role;

    public ProjectMember(String userId, ProjectRole projectRole){
        this.userId = userId;
        this.role = projectRole;
    }

    public static ProjectMember createMinimumMember(String userId){
        return new ProjectMember(userId,ProjectRole.VIEWER);
    }

    public static ProjectMember createNormalMember(String userId){
        return new ProjectMember(userId,ProjectRole.EDITOR);
    }

    public static ProjectMember createOwnerMember(String userId){
        return new ProjectMember(userId,ProjectRole.AUTHOR);
    }


    public String getUserId() {
        return userId;
    }

    public ProjectRole getRole() {
        return role;
    }

    public void setRole(ProjectRole role) {
        this.role = role;
    }
}