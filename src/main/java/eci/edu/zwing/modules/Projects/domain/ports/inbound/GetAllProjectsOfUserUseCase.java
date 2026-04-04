package eci.edu.zwing.modules.Projects.domain.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.model.Project;

import java.util.List;

public interface GetAllProjectsOfUserUseCase {
    List<Project> execute(CommandDTOs.GetUserProjectsDTO dto);
}
