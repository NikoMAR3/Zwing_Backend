package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.application.ports.outbound.UserQueryInPort;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ProjectMember;
import eci.edu.zwing.modules.projects.domain.ports.inbound.CreateProjectUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    @Autowired
    private UserQueryInPort userQueryInPort;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.CreateProjectCommandDTO dto) {
        projectRepository.save(new Project(
                dto.name(),
                ProjectMember.createOwnerMember(dto.ownerId())
                ));
    }
}
