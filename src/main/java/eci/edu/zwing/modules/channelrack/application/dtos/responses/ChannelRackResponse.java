package eci.edu.zwing.modules.channelrack.application.dtos.responses;

import java.util.List;

public sealed interface ChannelRackResponse {
    record RackResponse(
            String rackId,
            List<ChannelResponse> channels,
            long version
    ) implements ChannelRackResponse {}
    record ChannelResponse(
            String id,
            String name,
            String sampleId,
            float volume,
            boolean isMute,
            List<Boolean> steps // Enviamos una lista de booleanos para que el frontend sepa cuáles están activos
    ) implements ChannelRackResponse{}
}
