package eci.edu.zwing.modules.projects.domain.model;


import java.util.List;

public class Project {

    public String id;
    public String name;
    public List<ProjectMember> members;
    public List<ToolRef> tools;

    public Project addProjectMember(ProjectMember member) {
        return this;
    }

    public void addToolRef(ToolRef toolRef) {
    }

    public void removeProjectMember(String userId) {
    }

    public void removeToolRef(String toolRefId) {
    }

    public List<ToolRef> getToolRefs() {
        return tools;
    }

    public List<ProjectMember> getMembers() {
        return members;
    }

    public void setMemberRole(String userId, ProjectRole role) {
    }
}