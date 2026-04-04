package eci.edu.zwing.modules.projects.domain.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;

public interface GetProjectUseCase {
    Project execute(CommandDTOs.GetProjectCommandDTO dto);
}
