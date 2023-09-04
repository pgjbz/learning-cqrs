package dev.pgjbz.account.query.infrastructure.consumers;

import dev.pgjbz.account.common.events.AccountClosedEvent;
import dev.pgjbz.account.common.events.AccountOpenedEvent;
import dev.pgjbz.account.common.events.FundsDepositedEvent;
import dev.pgjbz.account.common.events.FundsWithdrawnEvent;
import dev.pgjbz.account.query.infrastructure.handlers.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountEventConsumer implements EventConsumer {

    private final EventHandler eventHandler;

    @Override
    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final AccountOpenedEvent event, final Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final FundsDepositedEvent event, final Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final FundsWithdrawnEvent event, final Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final AccountClosedEvent event, final Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
