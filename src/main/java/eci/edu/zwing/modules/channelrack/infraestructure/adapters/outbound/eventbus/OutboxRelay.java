package eci.edu.zwing.modules.channelrack.infraestructure.adapters.outbound.eventbus;

import eci.edu.zwing.modules.channelrack.domain.ports.outbound.OutboxStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxRelay {

    private final OutboxStore outboxStore;
    private final RedisTemplate<String, String> redis;

    public OutboxRelay(OutboxStore outboxStore, RedisTemplate<String, String> redis) {
        this.outboxStore = outboxStore;
        this.redis = redis;
    }

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void relay() {
        List<OutboxEntry> pending = outboxStore.findUnpublished(50);

        for (OutboxEntry entry : pending) {
            redis.convertAndSend(
                    "channelrack." + entry.aggregateId(),  // canal por agregado
                    entry.payload()
            );
            outboxStore.markPublished(entry.id());
        }
    }
}