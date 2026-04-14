package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.inbound.GetAllProjectsOfUserUseCase;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProjectsOfUserUseCaseImpl implements GetAllProjectsOfUserUseCase {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> execute(CommandDTOs.GetUserProjectsDTO dto) {

        return projectRepository.getProjectsByUserId(dto.userId());
    }
}
