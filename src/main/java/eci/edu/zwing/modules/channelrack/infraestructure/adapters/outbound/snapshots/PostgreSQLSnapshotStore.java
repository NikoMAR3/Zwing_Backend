package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.snapshots;

import com.fasterxml.jackson.databind.ObjectMapper;
import eci.edu.zwing.modules.channelrack.domain.ports.outbound.SnapshotStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.postgresql.util.PGobject;

import java.util.Optional;

@Component
public class PostgreSQLSnapshotStore implements SnapshotStore {

    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;

    public PostgreSQLSnapshotStore(JdbcTemplate jdbc, ObjectMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(Snapshot snapshot) {
        if (!(snapshot instanceof Snapshot.ChannelRackSnapshot crSnapshot)) {
            throw new IllegalArgumentException("Unsupported snapshot type");
        }

        String sql = """
            INSERT INTO snapshots 
            (channel_rack_id, version, snapshot_data, created_at)
            VALUES (?, ?, ?, NOW())
            ON CONFLICT (channel_rack_id) 
            DO UPDATE SET version = ?, snapshot_data = ?, created_at = NOW()
            """;

        try {
            String snapshotJson = mapper.writeValueAsString(crSnapshot);
            PGobject jsonb = new PGobject();
            jsonb.setType("jsonb");
            jsonb.setValue(snapshotJson);

            jdbc.update(sql,
                    crSnapshot.channelRackId(),
                    crSnapshot.version(),
                    jsonb,
                    crSnapshot.version(),
                    jsonb
            );
        } catch (Exception e) {
            throw new RuntimeException("Error saving snapshot for: " +
                    crSnapshot.channelRackId(), e);
        }
    }

    @Override
    public Optional<Snapshot.ChannelRackSnapshot> findLatest(String aggregateId) {
        String sql = """
            SELECT channel_rack_id, version, snapshot_data, created_at
            FROM snapshots
            WHERE channel_rack_id = ?
            ORDER BY version DESC
            LIMIT 1
            """;

        return jdbc.query(sql, (rs) -> {
            if (rs.next()) {
                try {
                    String snapshotJson = rs.getString("snapshot_data");
                    return Optional.of(
                            mapper.readValue(snapshotJson, Snapshot.ChannelRackSnapshot.class)
                    );
                } catch (Exception e) {
                    throw new RuntimeException("Error mapping snapshot", e);
                }
            }
            return Optional.empty();
        }, aggregateId);
    }
}