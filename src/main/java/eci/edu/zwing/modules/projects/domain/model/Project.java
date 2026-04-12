package eci.edu.zwing.modules.projects.domain.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {

    public String id;
    public String name;
    public String ownerId;
    public List<ProjectMember> members;
    public List<ToolRef> tools;

    public Project(String name, ProjectMember owner){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.members = new ArrayList<>();
        members.add(owner);
        this.tools = new ArrayList<>();
        this.ownerId = owner.getUserId();
    }

    public void addProjectMember(ProjectMember member) {
        members.add(member);
    }

    public void removeProjectMember(String userId) {
        members.removeIf(member -> member.getUserId().equals(userId));
    }

    public void addToolRef(ToolRef toolRef) {
        tools.add(toolRef);
    }

    public void removeToolRef(String toolRefId) {
        tools.removeIf(toolRef -> toolRef.toolId().equals(toolRefId));
    }

    public List<ToolRef> getToolRefs() {
        return tools;
    }

    public List<ProjectMember> getMembers() {
        return members;
    }

    public String getId() {
        return id;
    }

    public List<ToolRef> getTools() {
        return tools;
    }

    public String getName() {
        return name;
    }


    public void setMemberRole(String userId, ProjectRole role) {
        members.forEach(member -> {
            if (member.getUserId().equals(userId)) {
                member.setRole(role);
            }
        });
    }

    public String getOwnerId() {
        return ownerId;
    }
}