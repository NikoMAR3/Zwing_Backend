package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.RemoveProjectMemberUseCase;

public class RemoveProjectMemberUseCaseImpl implements RemoveProjectMemberUseCase {
    @Override
    public void execute(CommandDTOs.RemoveProjectMemberCommandDTO dto) {

    }
}
