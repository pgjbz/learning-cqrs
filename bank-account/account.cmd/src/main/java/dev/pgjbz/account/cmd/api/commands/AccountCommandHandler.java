package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.account.cmd.domain.AccountAggregate;
import dev.pgjbz.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        final var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.id());
        aggregate.depositFunds(command.amount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.id());
        final BigDecimal commandAmount = command.amount().abs();
        log.info("withdraw '{}' from account '{}' with balance '{}'", commandAmount, command.id(), aggregate.getBalance());
        if (commandAmount.compareTo(aggregate.getBalance()) > 0)
            throw new IllegalStateException("Withdraw declined, insufficient funds!");
        aggregate.withdrawFunds(commandAmount);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.id());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
