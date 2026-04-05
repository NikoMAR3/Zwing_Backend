package eci.edu.zwing.modules.sessions.infrastructure.ports;

import eci.edu.zwing.modules.sessions.domain.ports.inbound.CreateSessionUseCase;
import eci.edu.zwing.modules.sessions.domain.ports.inbound.RecordSessionActivityUseCase;
import eci.edu.zwing.modules.sessions.infrastructure.dtos.SessionsRequest;
import eci.edu.zwing.modules.sessions.infrastructure.dtos.SessionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

    private final CreateSessionUseCase createSessionUseCase;
    //private final RecordSessionActivityUseCase recordSessionActivityUseCase;
//    private final CloseSessionUseCase closeSessionUseCase;

    public SessionsController(
            CreateSessionUseCase createSessionUseCase)//,
            //RecordSessionActivityUseCase recordSessionActivityUseCase )//,
//            CloseSessionUseCase closeSessionUseCase)
    {
        this.createSessionUseCase = createSessionUseCase;
        //this.recordSessionActivityUseCase = recordSessionActivityUseCase;
//        this.getConnectedUsersStatisticsUseCase = getConnectedUsersStatisticsUseCase;
//        this.closeSessionUseCase = closeSessionUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createSession(
            @RequestBody SessionsRequest.CreateSessionRequest request) {
        createSessionUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();//.body(new SessionsResponse.SessionCreatedResponse());
    }

//    @PostMapping("/{sessionId}/activity")
//    public ResponseEntity<Void> recordActivity(
//            @PathVariable String sessionId) {
//        var request = new SessionsRequest.RecordSessionActivityRequest(sessionId);
//        recordSessionActivityUseCase.execute(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{sessionId}")
//    public ResponseEntity<Void> closeSession(
//            @PathVariable String sessionId,
//            @RequestParam(required = false) String reason) {
//        var request = new SessionsRequest.CloseSessionRequest(sessionId, reason);
//        closeSessionUseCase.execute(request);
//        return ResponseEntity.ok().build();
//    }
}
