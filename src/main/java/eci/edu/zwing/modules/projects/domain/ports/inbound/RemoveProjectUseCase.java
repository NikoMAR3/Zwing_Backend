package eci.edu.zwing.modules.projects.domain.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;

public interface RemoveProjectUseCase {
    void execute(CommandDTOs.RemoveProjectCommandDTO dto);
}
