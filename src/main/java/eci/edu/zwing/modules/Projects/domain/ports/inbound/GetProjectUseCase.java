package eci.edu.zwing.modules.Projects.domain.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.model.Project;

public interface GetProjectUseCase {
    Project execute(CommandDTOs.GetProjectCommandDTO dto);
}
