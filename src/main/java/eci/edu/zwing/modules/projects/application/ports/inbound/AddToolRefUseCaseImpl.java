package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ToolRef;
import eci.edu.zwing.modules.projects.domain.model.WorkplaceType;
import eci.edu.zwing.modules.projects.domain.ports.inbound.AddToolRefUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddToolRefUseCaseImpl implements AddToolRefUseCase {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.AddToolRefUseCaseDTO dto) {
        Project project = projectRepository.getProject(dto.projectId());
        project.addToolRef(new ToolRef(
                dto.toolRefId(),
                WorkplaceType.valueOf(dto.type().toUpperCase())
        ));
        projectRepository.save(project);
    }
}
