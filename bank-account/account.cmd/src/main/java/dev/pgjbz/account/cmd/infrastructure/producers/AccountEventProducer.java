package dev.pgjbz.account.cmd.infrastructure.producers;

import dev.pgjbz.cqrs.core.events.BaseEvent;
import dev.pgjbz.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(final String topicName, final BaseEvent baseEvent) {
        kafkaTemplate.send(topicName, baseEvent.id(), baseEvent);
    }
}
