package eci.edu.zwing.modules.channelrack.application.mappers;

import eci.edu.zwing.modules.channelrack.domain.model.Channel;
import eci.edu.zwing.modules.channelrack.domain.model.ChannelRack;
import eci.edu.zwing.modules.channelrack.domain.model.Step;
import eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.snapshots.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SnapshotMapper {

    /**
     * Mapea un ChannelRackSnapshot a una entidad ChannelRack
     */
    public static ChannelRack snapshotToEntity(Snapshot.ChannelRackSnapshot snapshot) {
        return new ChannelRack(
                snapshot.channelRackId(),
                snapshot.channels().stream()
                        .map(SnapshotMapper::channelSnapshotToEntity)
                        .collect(Collectors.toCollection(ArrayList::new)),
                snapshot.version()
        );
    }

    /**
     * Mapea un ChannelSnapshot a una entidad Channel
     */
    private static Channel channelSnapshotToEntity(Snapshot.ChannelSnapshot snapshot) {

        Step[] steps = new Step[16];
        for (int i = 0; i < snapshot.steps().size(); i++) {
            Step step = new Step();
            if (snapshot.steps().get(i)) {
                step.activate();
            }
            steps[i] = step;
        }

        return new Channel(
                snapshot.channelId(),
                snapshot.name(),
                steps,
                snapshot.sampleId(),
                snapshot.volume(),
                snapshot.mute()
        );
    }

    /**
     * Mapea una entidad ChannelRack a un ChannelRackSnapshot
     */
    public static Snapshot.ChannelRackSnapshot entityToSnapshot(ChannelRack channelRack) {
        List<Snapshot.ChannelSnapshot> channelSnapshots = channelRack.getChannels().stream()
                .map(SnapshotMapper::entityToChannelSnapshot)
                .toList();

        return new Snapshot.ChannelRackSnapshot(
                channelRack.getChannelRackId(),
                channelSnapshots,
                channelRack.getVersion()
        );
    }

    /**
     * Mapea una entidad Channel a un ChannelSnapshot
     */
    private static Snapshot.ChannelSnapshot entityToChannelSnapshot(Channel channel) {
        List<Boolean> steps = extractStepsAsBooleanList(channel.getSteps());

        return new Snapshot.ChannelSnapshot(
                channel.getChannelId(),
                channel.getName(),
                channel.getSampleId(),
                channel.getVolume(),
                channel.isMute(),
                steps
        );
    }

    /**
     * Convierte el array de Step[] a List<Boolean>
     */
    private static List<Boolean> extractStepsAsBooleanList(Step[] steps) {
        if (steps == null) {
            return List.of();
        }
        return List.of(steps).stream()
                .map(Step::isActive)
                .toList();
    }
}