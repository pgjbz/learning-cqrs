package dev.pgjbz.account.query.infrastructure.handlers.impl;

import dev.pgjbz.account.common.events.AccountClosedEvent;
import dev.pgjbz.account.common.events.AccountOpenedEvent;
import dev.pgjbz.account.common.events.FundsDepositedEvent;
import dev.pgjbz.account.common.events.FundsWithdrawnEvent;
import dev.pgjbz.account.query.domain.models.BankAccount;
import dev.pgjbz.account.query.domain.repository.BankAccountRepository;
import dev.pgjbz.account.query.infrastructure.handlers.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BankAccountEventHandler implements EventHandler {

    private final BankAccountRepository accountRepository;

    @Override
    public void on(final AccountOpenedEvent event) {
        final BankAccount bankAccount  = BankAccount.builder()
                .id(event.id())
                .accountHolder(event.accountHolder())
                .creationDate(event.createdDate())
                .accountType(event.accountType())
                .balance(event.openingBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(final FundsDepositedEvent event) {
        accountRepository.findById(event.id())
            .ifPresent(bankAccount -> {
                final BigDecimal currentBalance = bankAccount.getBalance();
                final BigDecimal latestBalance = currentBalance.add(event.amount());
                bankAccount.setBalance(latestBalance);
                accountRepository.save(bankAccount);
            });
    }

    @Override
    public void on(final FundsWithdrawnEvent event) {
        accountRepository.findById(event.id())
            .ifPresent(bankAccount -> {
                final BigDecimal currentBalance = bankAccount.getBalance();
                final BigDecimal latestBalance = currentBalance.subtract(event.amount().abs());
                bankAccount.setBalance(latestBalance);
                accountRepository.save(bankAccount);
            });
    }

    @Override
    public void on(final AccountClosedEvent event) {
        accountRepository.deleteById(event.id());
    }
}
