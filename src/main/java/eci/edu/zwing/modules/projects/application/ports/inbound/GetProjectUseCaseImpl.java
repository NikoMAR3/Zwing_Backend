package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.inbound.GetProjectUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProjectUseCaseImpl implements GetProjectUseCase {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project execute(CommandDTOs.GetProjectCommandDTO dto) {
        return projectRepository.getProject(dto.projectId());
    }
}
