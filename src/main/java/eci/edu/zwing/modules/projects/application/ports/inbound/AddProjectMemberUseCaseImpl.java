package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.AddProjectMemberUseCase;
import org.springframework.stereotype.Service;

@Service
public class AddProjectMemberUseCaseImpl implements AddProjectMemberUseCase {
    @Override
    public void execute(CommandDTOs.AddProjectMemberCommandDTO dto) {

    }
}
