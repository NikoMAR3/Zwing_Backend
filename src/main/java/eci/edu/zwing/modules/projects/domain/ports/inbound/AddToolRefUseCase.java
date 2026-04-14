package eci.edu.zwing.modules.projects.domain.ports.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs;

public interface AddToolRefUseCase {
    void execute(CommandDTOs.AddToolRefUseCaseDTO dto);
}

