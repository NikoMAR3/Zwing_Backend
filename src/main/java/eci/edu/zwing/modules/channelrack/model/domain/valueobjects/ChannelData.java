package eci.edu.zwing.modules.channelrack.model.domain.valueobjects;

public record ChannelData(
        String name,
        String sampleId,
        int volume,
        boolean mute
) {}
