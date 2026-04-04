package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.snapshots;

import java.util.List;

public sealed interface Snapshot {
    record ChannelRackSnapshot(
            String channelRackId,
            List<ChannelSnapshot> channels,
            long version
    ) implements Snapshot {}

    record ChannelSnapshot(
            String channelId,
            String name,
            String sampleId,
            float volume,
            boolean mute,
            List<Boolean> steps
    ) implements Snapshot{}
}
