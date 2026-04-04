package eci.edu.zwing.modules.Projects.infraestructure;

import eci.edu.zwing.modules.Projects.application.ports.dtos.CommandDTOs;
import eci.edu.zwing.modules.Projects.domain.ports.inbound.*;
import eci.edu.zwing.modules.Projects.domain.model.Project;
import eci.edu.zwing.modules.Projects.domain.ports.inbound.AddProjectMemberUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final AddProjectMemberUseCase addProjectMemberUseCase;
    private final RemoveProjectMemberUseCase removeProjectMemberUseCase;
    private final SetProjectMemberRoleUseCase setProjectMemberRoleUseCase;
    private final AddSnapshotRefUseCase addSnapshotRefUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final GetAllProjectsOfUserUseCase getAllProjectsOfUserUseCase;
    private final RemoveProjectUseCase removeProjectUseCase;

    public ProjectController(
            AddProjectMemberUseCase addProjectMemberUseCase,
            RemoveProjectMemberUseCase removeProjectMemberUseCase,
            SetProjectMemberRoleUseCase setProjectMemberRoleUseCase,
            AddSnapshotRefUseCase addSnapshotRefUseCase,
            GetProjectUseCase getProjectUseCase,
            GetAllProjectsOfUserUseCase getAllProjectsOfUserUseCase,
            RemoveProjectUseCase removeProjectUseCase) {
        this.addProjectMemberUseCase = addProjectMemberUseCase;
        this.removeProjectMemberUseCase = removeProjectMemberUseCase;
        this.setProjectMemberRoleUseCase = setProjectMemberRoleUseCase;
        this.addSnapshotRefUseCase = addSnapshotRefUseCase;
        this.getProjectUseCase = getProjectUseCase;
        this.getAllProjectsOfUserUseCase = getAllProjectsOfUserUseCase;
        this.removeProjectUseCase = removeProjectUseCase;
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable String projectId) {
        CommandDTOs.GetProjectCommandDTO dto = new CommandDTOs.GetProjectCommandDTO(projectId);
        Project project = getProjectUseCase.execute(dto);
        return ResponseEntity.ok(project);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getAllProjectsOfUser(@PathVariable String userId) {
        CommandDTOs.GetUserProjectsDTO dto = new CommandDTOs.GetUserProjectsDTO(userId);
        getAllProjectsOfUserUseCase.execute(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable String projectId) {
        CommandDTOs.RemoveProjectCommandDTO dto = new CommandDTOs.RemoveProjectCommandDTO(projectId);
        removeProjectUseCase.execute(dto);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{projectId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable String projectId,
            @RequestBody CommandDTOs.AddProjectMemberCommandDTO dto) {
        addProjectMemberUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable String projectId,
            @PathVariable String userId) {
        CommandDTOs.RemoveProjectMemberCommandDTO dto = new CommandDTOs.RemoveProjectMemberCommandDTO(projectId, userId);
        removeProjectMemberUseCase.execute(dto);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{projectId}/members/{userId}/role")
    public ResponseEntity<Void> setMemberRole(
            @PathVariable String projectId,
            @PathVariable String userId,
            @RequestBody CommandDTOs.SetProjectMemberRoleUseCaseDTO dto) {
        setProjectMemberRoleUseCase.execute(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/snapshots")
    public ResponseEntity<Void> addSnapshot(
            @PathVariable String projectId,
            @RequestBody CommandDTOs.AddSnapshotRefUseCaseDTO dto) {
        addSnapshotRefUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
