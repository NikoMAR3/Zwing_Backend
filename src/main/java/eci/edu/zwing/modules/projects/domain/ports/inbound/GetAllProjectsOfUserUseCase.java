package eci.edu.zwing.modules.projects.domain.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;

import java.util.List;

public interface GetAllProjectsOfUserUseCase {
    List<Project> execute(CommandDTOs.GetUserProjectsDTO dto);
}
