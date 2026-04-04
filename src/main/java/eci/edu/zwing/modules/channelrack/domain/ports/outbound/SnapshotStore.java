package eci.edu.zwing.modules.channelrack.domain.ports.outbound;

import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.snapshots.Snapshot;

import java.util.Optional;

public interface SnapshotStore {
    void save(Snapshot snapshot);
    Optional<Snapshot.ChannelRackSnapshot> findLatest(String aggregateId);
}