package eci.edu.zwing.modules.projects.infraestructure.inbound;

import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs.GetProjectCommandDTO;
import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs.GetUserProjectsDTO;
import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs.RemoveProjectCommandDTO;
import eci.edu.zwing.modules.projects.application.ports.dtos.CommandDTOs.RemoveProjectMemberCommandDTO;
import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.ports.inbound.*;
import eci.edu.zwing.modules.projects.infraestructure.inbound.dtos.ProjectRequestDTOs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final AddProjectMemberUseCase addProjectMemberUseCase;
    private final RemoveProjectMemberUseCase removeProjectMemberUseCase;
    private final SetProjectMemberRoleUseCase setProjectMemberRoleUseCase;
    private final AddToolRefUseCase addToolRefUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final GetAllProjectsOfUserUseCase getAllProjectsOfUserUseCase;
    private final RemoveProjectUseCase removeProjectUseCase;

    @Autowired
    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            AddProjectMemberUseCase addProjectMemberUseCase,
            RemoveProjectMemberUseCase removeProjectMemberUseCase,
            SetProjectMemberRoleUseCase setProjectMemberRoleUseCase,
            AddToolRefUseCase addToolRefUseCase,
            GetProjectUseCase getProjectUseCase,
            GetAllProjectsOfUserUseCase getAllProjectsOfUserUseCase,
            RemoveProjectUseCase removeProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
        this.addProjectMemberUseCase = addProjectMemberUseCase;
        this.removeProjectMemberUseCase = removeProjectMemberUseCase;
        this.setProjectMemberRoleUseCase = setProjectMemberRoleUseCase;
        this.addToolRefUseCase = addToolRefUseCase;
        this.getProjectUseCase = getProjectUseCase;
        this.getAllProjectsOfUserUseCase = getAllProjectsOfUserUseCase;
        this.removeProjectUseCase = removeProjectUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectRequestDTOs.CreateProjectRequest req) {
        createProjectUseCase.execute(ProjectDTOMapper.mapToCreateProjectCommand(req));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable String projectId) {
        Project project = getProjectUseCase.execute(new GetProjectCommandDTO(projectId));
        return ResponseEntity.ok(project);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getAllProjectsOfUser(@PathVariable String userId) {
        List<Project> projects = getAllProjectsOfUserUseCase.execute(new GetUserProjectsDTO(userId));
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable String projectId) {
        removeProjectUseCase.execute(new RemoveProjectCommandDTO(projectId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable String projectId,
            @RequestBody ProjectRequestDTOs.AddMemberRequest req) {
        addProjectMemberUseCase.execute(ProjectDTOMapper.mapToAddProjectMemberCommand(projectId, req));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable String projectId,
            @PathVariable String userId) {
        removeProjectMemberUseCase.execute(new RemoveProjectMemberCommandDTO(projectId, userId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{projectId}/members/{userId}/role")
    public ResponseEntity<Void> setMemberRole(
            @PathVariable String projectId,
            @PathVariable String userId,
            @RequestBody ProjectRequestDTOs.SetMemberRoleRequest req) {
        setProjectMemberRoleUseCase.execute(ProjectDTOMapper.mapToSetProjectMemberRole(projectId, userId, req));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/snapshots")
    public ResponseEntity<Void> addSnapshot(
            @PathVariable String projectId,
            @RequestBody ProjectRequestDTOs.AddSnapshotRequest req) {
        addToolRefUseCase.execute(ProjectDTOMapper.mapToAddSnapshotRef(projectId, req));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}