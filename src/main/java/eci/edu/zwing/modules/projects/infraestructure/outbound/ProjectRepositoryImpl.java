package eci.edu.zwing.modules.projects.infraestructure.outbound;

import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectRepositoryImpl implements ProjectRepository {
    @Override
    public void save(Project project) {

    }

    @Override
    public void removeProject(String projectId) {

    }

    @Override
    public Project getProject(String projectId) {
        return null;
    }

    @Override
    public Project getProjectByUserId(String projectId) {
        return null;
    }
}
