package eci.edu.zwing.modules.Projects.application.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.model.Project;
import eci.edu.zwing.modules.Projects.domain.ports.inbound.GetProjectUseCase;

public class GetProjectUseCaseImpl implements GetProjectUseCase {
    @Override
    public Project execute(CommandDTOs.GetProjectCommandDTO dto) {
        return null;
    }
}
