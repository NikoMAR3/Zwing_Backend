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

    /**
     * Agregar un canal al rack
     */
    @PostMapping("/{rackId}/channels")
    public ResponseEntity<Void> addChannel(
            @PathVariable String rackId,
            @RequestBody AddChannelRequest request) {
        ChannelRackCommand.AddChannel command = new ChannelRackCommand.AddChannel(
                rackId,
                request.channelID(),
                request.name(),
                request.sampleId(),
                request.expectedVersion()
        );
        addChannelUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Remover un canal del rack
     */
    @DeleteMapping("/{rackId}/channels/{channelId}")
    public ResponseEntity<Void> removeChannel(
            @PathVariable String rackId,
            @PathVariable String channelId,
            @RequestParam Long expectedVersion) {
        ChannelRackCommand.RemoveChannel command = new ChannelRackCommand.RemoveChannel(rackId, channelId, expectedVersion);
        removeChannelUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activar un step en el sistema
     */
    @PutMapping("/{rackId}/channels/{channelId}/steps/{stepIndex}/activate")
    public ResponseEntity<Void> activateStep(
            @PathVariable String rackId,
            @PathVariable String channelId,
            @PathVariable int stepIndex,
            @RequestParam Long expectedVersion) {
        ChannelRackCommand.ActivateStep command = new ChannelRackCommand.ActivateStep(rackId, channelId, stepIndex, expectedVersion);
        activateStepUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    /**
     * Desactivar un step en el sistema
     */
    @PutMapping("/{rackId}/channels/{channelId}/steps/{stepIndex}/deactivate")
    public ResponseEntity<Void> deactivateStep(
            @PathVariable String rackId,
            @PathVariable String channelId,
            @PathVariable int stepIndex,
            @RequestParam Long expectedVersion) {
        ChannelRackCommand.DeactivateStep command = new ChannelRackCommand.DeactivateStep(rackId, channelId, stepIndex, expectedVersion);
        deactivateStepUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    /**
     * Record simple para el request de AddChannel
     */
    public record AddChannelRequest(String channelID, String name, String sampleId, Long expectedVersion) {}
}