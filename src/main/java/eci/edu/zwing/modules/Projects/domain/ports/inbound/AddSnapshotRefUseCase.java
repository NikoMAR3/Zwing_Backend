package eci.edu.zwing.modules.Projects.domain.ports.inbound;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;

public interface AddSnapshotRefUseCase {
    void execute(CommandDTOs.AddSnapshotRefUseCaseDTO dto);
}

