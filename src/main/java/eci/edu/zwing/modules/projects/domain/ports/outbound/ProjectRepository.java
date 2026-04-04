package eci.edu.zwing.modules.projects.domain.ports.outbound;

import eci.edu.zwing.modules.projects.domain.model.Project;

public interface ProjectRepository {

    void save(Project project);

    void removeProject(String projectId);

    Project getProject(String projectId);

    Project getProjectByUserId(String projectId);
}
