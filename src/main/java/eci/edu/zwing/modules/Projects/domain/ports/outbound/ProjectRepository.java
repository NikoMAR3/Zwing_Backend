package eci.edu.zwing.modules.Projects.domain.ports.outbound;

import eci.edu.zwing.modules.Projects.domain.model.Project;

public interface ProjectRepository {

    void save(Project project);

    void removeProject(String projectId);

    Project getProject(String projectId);

    Project getProjectByUserId(String projectId);
}
