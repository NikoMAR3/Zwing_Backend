package eci.edu.zwing.modules.channelrack.infraestructure.adapters.inbound;

import eci.edu.zwing.modules.channelrack.application.dtos.commands.ChannelRackCommand;
import eci.edu.zwing.modules.channelrack.application.dtos.queries.ChannelRackQuery;
import eci.edu.zwing.modules.channelrack.domain.ports.inbound.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/channel-rack")
public class ChannelRackController {

    private final DeactivateStepUseCase deactivateStepUseCase;
    private final ActivateStepUseCase activateStepUseCase;
    private final RemoveChannelUseCase removeChannelUseCase;
    private final AddChannelUseCase addChannelUseCase;
    private final GetChannelsUseCase getChannelsUseCase;

    @Autowired
    public ChannelRackController(
            DeactivateStepUseCase deactivateStepUseCase,
            ActivateStepUseCase activateStepUseCase,
            RemoveChannelUseCase removeChannelUseCase,
            AddChannelUseCase addChannelUseCase,
            GetChannelsUseCase getChannelsUseCase) {
        this.deactivateStepUseCase = deactivateStepUseCase;
        this.activateStepUseCase = activateStepUseCase;
        this.removeChannelUseCase = removeChannelUseCase;
        this.addChannelUseCase = addChannelUseCase;
        this.getChannelsUseCase = getChannelsUseCase;
    }

    /**
     * Obtener información del rack de canales
     */
    @GetMapping("/{rackId}/channels")
    public ResponseEntity<?> getChannels(@PathVariable String rackId) {
        return ResponseEntity.ok(
                getChannelsUseCase.execute(
                        new ChannelRackQuery.ListChannelsInRack(rackId)
                )
        );
    }
}