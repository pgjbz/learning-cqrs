package dev.pgjbz.account.query.infrastructure.consumers;

import dev.pgjbz.account.common.events.AccountClosedEvent;
import dev.pgjbz.account.common.events.AccountOpenedEvent;
import dev.pgjbz.account.common.events.FundsDepositedEvent;
import dev.pgjbz.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload final AccountOpenedEvent event, final Acknowledgment ack);

    void consume(@Payload final FundsDepositedEvent event, final Acknowledgment ack);

    void consume(@Payload final FundsWithdrawnEvent event, final Acknowledgment ack);

    void consume(@Payload final AccountClosedEvent event, final Acknowledgment ack);
}
