package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.RemoveProjectUseCase;

public class RemoveProjectUseCaseImpl implements RemoveProjectUseCase {
    @Override
    public void execute(CommandDTOs.RemoveProjectCommandDTO dto) {

    }
}
