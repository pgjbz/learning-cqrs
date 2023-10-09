package dev.pgjbz.account.cmd.domain.aggregates;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import dev.pgjbz.account.cmd.api.commands.OpenAccountCommand;
import dev.pgjbz.account.cmd.domain.exception.AccountClosedException;
import dev.pgjbz.account.cmd.domain.exception.InvalidAmountException;
import dev.pgjbz.account.common.events.AccountClosedEvent;
import dev.pgjbz.account.common.events.AccountOpenedEvent;
import dev.pgjbz.account.common.events.FundsDepositedEvent;
import dev.pgjbz.account.common.events.FundsWithdrawnEvent;
import dev.pgjbz.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    @Getter
    private BigDecimal balance;

    public AccountAggregate(final OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.id())
                .accountHolder(command.accountHolder())
                .createdDate(LocalDateTime.now())
                .accountType(command.accountType())
                .openingBalance(command.openingBalance())
                .build());
    }

    public void apply(final AccountOpenedEvent event) {
        this.id = event.id();
        this.active = true;
        this.balance = event.openingBalance();
    }

    public void depositFunds(final BigDecimal amount) {
        if (!this.active)
            throw new AccountClosedException("funds cannot be deposited into a closed account!");

        if (amount.signum() < 0 || BigDecimal.ZERO.equals(amount))
            throw new InvalidAmountException("the deposit amount mus be greater than zero");
        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(final FundsDepositedEvent event) {
        this.id = event.id();
        this.balance = event.amount().add(this.balance);
    }

    public void withdrawFunds(final BigDecimal amount) {
        if (!this.active)
            throw new AccountClosedException("funds cannot be withdraw from a closed account!");
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(final FundsWithdrawnEvent event) {
        this.id = event.id();
        this.balance = this.balance.abs().subtract(event.amount().abs());
    }

    public void closeAccount() {
        if (!this.active)
            throw new AccountClosedException("the bank account has already been closed!");
        raiseEvent(AccountClosedEvent.builder().id(this.id).build());
    }

    public void apply(final AccountClosedEvent event) {
        this.id = event.id();
        this.active = false;
    }
}
