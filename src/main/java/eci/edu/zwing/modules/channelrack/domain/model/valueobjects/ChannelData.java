package eci.edu.zwing.modules.channelrack.domain.model.valueobjects;

public record ChannelData(
        String name,
        String sampleId,
        int volume,
        boolean mute
) {}
