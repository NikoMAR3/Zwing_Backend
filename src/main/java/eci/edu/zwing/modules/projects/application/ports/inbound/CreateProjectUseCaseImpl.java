package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.application.ports.dtos.QueryDTOs;
import eci.edu.zwing.modules.projects.application.ports.outbound.UserQuery;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ProjectMember;
import eci.edu.zwing.modules.projects.domain.ports.inbound.CreateProjectUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.CreateProjectCommandDTO dto) {
        userQuery.getUserById(UUID.fromString(dto.ownerId()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        projectRepository.save(new Project(
                dto.name(),
                ProjectMember.createOwnerMember(dto.ownerId())
        ));
    }

}
