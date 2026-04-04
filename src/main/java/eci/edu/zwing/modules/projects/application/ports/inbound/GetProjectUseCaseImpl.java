package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.inbound.GetProjectUseCase;

public class GetProjectUseCaseImpl implements GetProjectUseCase {
    @Override
    public Project execute(CommandDTOs.GetProjectCommandDTO dto) {
        return null;
    }
}
