package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.inbound.GetAllProjectsOfUserUseCase;

import java.util.List;

public class GetAllProjectsOfUserUseCaseImpl implements GetAllProjectsOfUserUseCase {
    @Override
    public List<Project> execute(CommandDTOs.GetUserProjectsDTO dto) {
        return List.of();
    }
}
