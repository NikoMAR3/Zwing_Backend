package eci.edu.zwing.modules.Projects.domain.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;

public interface SetProjectMemberRoleUseCase {
    void execute(CommandDTOs.SetProjectMemberRoleUseCaseDTO dto);
}
