package eci.edu.zwing.modules.projects.application.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.projects.domain.ports.inbound.CreateProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectUseCaseImpl implements CreateProjectUseCase {
    @Override
    public void execute(CommandDTOs.CreateProjectCommandDTO dto) {

    }
}
