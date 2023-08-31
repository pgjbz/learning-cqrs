package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.account.cmd.domain.AccountAggregate;
import dev.pgjbz.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
        final var aggregate = eventSourcingHandler.getById(command.id());
        aggregate.depositFunds(command.amount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        final AccountAggregate aggregate = eventSourcingHandler.getById(command.id());
        final BigDecimal commandAmount = command.amount();
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
