package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ProjectRole;
import eci.edu.zwing.modules.projects.domain.ports.inbound.SetProjectMemberRoleUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetProjectMemberRoleUseCaseImpl implements SetProjectMemberRoleUseCase {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void execute(CommandDTOs.SetProjectMemberRoleUseCaseDTO dto) {
        Project project = projectRepository.getProject(dto.projectId());
        project.setMemberRole(dto.userId(), ProjectRole.valueOf(dto.role()));
    }
}
