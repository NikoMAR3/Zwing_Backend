package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.application.ports.outbound.UserQuery;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ProjectMember;
import eci.edu.zwing.modules.projects.domain.ports.inbound.AddProjectMemberUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddProjectMemberUseCaseImpl implements AddProjectMemberUseCase {

    @Autowired
    private UserQuery userQuery;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.AddProjectMemberCommandDTO dto) {
        if((userQuery.getUserById(UUID.fromString(dto.userId())).isPresent())){
            Project project = projectRepository.getProject(dto.projectId());
            project.addProjectMember(ProjectMember.createMinimumMember(dto.userId()));
            projectRepository.save(project);
        }
    }
}
