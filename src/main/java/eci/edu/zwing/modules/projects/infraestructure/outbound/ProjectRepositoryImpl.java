package eci.edu.zwing.modules.projects.infraestructure.outbound;

import eci.edu.zwing.modules.projects.domain.model.Project;
import eci.edu.zwing.modules.projects.domain.model.ProjectMember;
import eci.edu.zwing.modules.projects.domain.model.ProjectRole;
import eci.edu.zwing.modules.projects.domain.model.ToolRef;
import eci.edu.zwing.modules.projects.domain.ports.outbound.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProjectRepositoryImpl implements ProjectRepository {

    private final JdbcTemplate jdbc;

    public ProjectRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(Project project) {
        // 1. Upsert del proyecto (esto ya estaba bien)
        jdbc.update("""
        INSERT INTO projects (id, name, owner_id)
        VALUES (?, ?, ?)
        ON CONFLICT (id) DO UPDATE
            SET name = EXCLUDED.name,
                owner_id = EXCLUDED.owner_id
        """,
                project.getId(), project.getName(), project.getOwnerId()
        );

        syncMembers(project);
        syncTools(project);
    }

    private void syncMembers(Project project) {
        // Trae los user_ids que ya están en BD
        List<String> existingIds = jdbc.queryForList("""
        SELECT user_id FROM project_members WHERE project_id = ?
        """,
                String.class,
                project.getId()
        );

        List<String> incomingIds = project.getMembers().stream()
                .map(ProjectMember::getUserId)
                .toList();

        // Los que están en BD pero no en el dominio → borrar
        List<String> toDelete = existingIds.stream()
                .filter(id -> !incomingIds.contains(id))
                .toList();

        // Los que están en el dominio pero no en BD → insertar
        List<ProjectMember> toInsert = project.getMembers().stream()
                .filter(m -> !existingIds.contains(m.getUserId()))
                .toList();

        // Los que están en ambos → actualizar solo el rol
        List<ProjectMember> toUpdate = project.getMembers().stream()
                .filter(m -> existingIds.contains(m.getUserId()))
                .toList();

        toDelete.forEach(userId ->
                jdbc.update("""
            DELETE FROM project_members WHERE project_id = ? AND user_id = ?
            """,
                        project.getId(), userId
                )
        );

        toInsert.forEach(m ->
                jdbc.update("""
            INSERT INTO project_members (user_id, role, project_id)
            VALUES (?, ?, ?)
            """,
                        m.getUserId(), m.getRole().name(), project.getId()
                )
        );

        toUpdate.forEach(m ->
                jdbc.update("""
            UPDATE project_members SET role = ?
            WHERE project_id = ? AND user_id = ?
            """,
                        m.getRole().name(), project.getId(), m.getUserId()
                )
        );
    }

    private void syncTools(Project project) {
        List<String> existingIds = jdbc.queryForList("""
        SELECT tool_id FROM tool_refs WHERE project_id = ?
        """,
                String.class,
                project.getId()
        );

        List<String> incomingIds = project.getTools().stream()
                .map(ToolRef::toolId)
                .toList();

        // Tools eliminadas → borrar
        existingIds.stream()
                .filter(id -> !incomingIds.contains(id))
                .forEach(id ->
                        jdbc.update("""
                    DELETE FROM tool_refs WHERE project_id = ? AND tool_id = ?
                    """,
                                project.getId(), id
                        )
                );

        // Tools nuevas → insertar
        project.getTools().stream()
                .filter(t -> !existingIds.contains(t.toolId()))
                .forEach(t ->
                        jdbc.update("""
                    INSERT INTO tool_refs (tool_id, project_id)
                    VALUES (?, ?)
                    """,
                                t.toolId(), project.getId()
                        )
                );
    }
    @Override
    public void removeProject(String projectId) {
        jdbc.update("DELETE FROM projects WHERE id = ?", projectId);
    }

    @Override
    public Project getProject(String projectId) {
        List<ProjectMember> members = jdbc.query("""
            SELECT user_id, role FROM project_members WHERE project_id = ?
            """,
                (rs, i) -> new ProjectMember(rs.getString("user_id"), ProjectRole.valueOf(rs.getString("role"))),
                projectId
        );

        if (members.isEmpty()) return null;

        return jdbc.queryForObject("""
            SELECT id, name, owner_id FROM projects WHERE id = ?
            """,
                (rs, i) -> buildProject(rs, members),
                projectId
        );
    }

    @Override
    public List<Project> getProjectsByUserId(String ownerId) {
        return jdbc.query("""
            SELECT p.id, p.name, p.owner_id FROM projects p WHERE p.owner_id = ?
            """,
                (rs, i) -> {
                    String projectId = rs.getString("id");
                    List<ProjectMember> members = jdbc.query("""
                    SELECT user_id, role FROM project_members WHERE project_id = ?
                    """,
                            (rs2, j) -> new ProjectMember(rs2.getString("user_id"), ProjectRole.valueOf(rs2.getString("role"))),
                            projectId
                    );
                    return buildProject(rs, members);
                },
                ownerId
        );
    }

    private Project buildProject(ResultSet rs, List<ProjectMember> members) throws SQLException, SQLException {
        String ownerId = rs.getString("owner_id");

        ProjectMember owner = members.stream()
                .filter(m -> m.getUserId().equals(ownerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Project project = new Project(rs.getString("name"), owner);

        members.stream()
                .filter(m -> !m.getUserId().equals(ownerId))
                .forEach(project::addProjectMember);

        return project;
    }
}
