package eci.edu.zwing.modules.channelrack.application.mappers;

import eci.edu.zwing.modules.channelrack.application.dtos.responses.ChannelRackResponse;
import eci.edu.zwing.modules.channelrack.domain.model.Channel;
import eci.edu.zwing.modules.channelrack.domain.model.Step;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelMapper {

    public static ChannelRackResponse.ChannelResponse toResponse(Channel channel) {
        if (channel == null) return null;
        List<Boolean> stepStatus = Arrays.stream(channel.getSteps())
                .map(Step::isActive)
                .collect(Collectors.toList());

        return new ChannelRackResponse.ChannelResponse(
                channel.getChannelId(),
                channel.getName(),
                channel.getSampleId(),
                channel.getVolume() != null ? channel.getVolume() : 0.0f,
                channel.isMute(),
                stepStatus
        );
    }
}
