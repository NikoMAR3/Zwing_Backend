package eci.edu.zwing.modules.Projects.application.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.ports.inbound.RemoveProjectUseCase;

public class RemoveProjectUseCaseImpl implements RemoveProjectUseCase {
    @Override
    public void execute(CommandDTOs.RemoveProjectCommandDTO dto) {

    }
}
