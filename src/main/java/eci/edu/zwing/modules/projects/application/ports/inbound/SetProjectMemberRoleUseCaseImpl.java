package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.SetProjectMemberRoleUseCase;
import org.springframework.stereotype.Service;

@Service
public class SetProjectMemberRoleUseCaseImpl implements SetProjectMemberRoleUseCase {
    @Override
    public void execute(CommandDTOs.SetProjectMemberRoleUseCaseDTO dto) {

    }
}
