package eci.edu.zwing.modules.Projects.application.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.ports.inbound.RemoveProjectMemberUseCase;

public class RemoveProjectMemberUseCaseImpl implements RemoveProjectMemberUseCase {
    @Override
    public void execute(CommandDTOs.RemoveProjectMemberCommandDTO dto) {

    }
}
