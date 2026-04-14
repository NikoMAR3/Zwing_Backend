package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.RemoveProjectUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveProjectUseCaseImpl implements RemoveProjectUseCase {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.RemoveProjectCommandDTO dto) {
        projectRepository.removeProject(dto.projectId());
    }
}
