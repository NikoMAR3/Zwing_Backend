package eci.edu.zwing.modules.projects.domain.ports.outbound;

import eci.edu.zwing.modules.projects.domain.model.Project;

import java.util.List;

public interface ProjectRepository {

    void save(Project project);

    void removeProject(String projectId);

    Project getProject(String projectId);

    List<Project> getProjectsByUserId(String userId);
}
