package eci.edu.zwing.modules.projects.domain.model;

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


    public String getUserId() {
        return userId;
    }



}