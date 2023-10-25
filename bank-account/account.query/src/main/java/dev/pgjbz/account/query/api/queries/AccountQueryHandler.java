package dev.pgjbz.account.query.api.queries;

import dev.pgjbz.account.query.domain.repository.BankAccountRepository;
import dev.pgjbz.cqrs.core.domain.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.List.copyOf;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        return copyOf(bankAccountRepository.findAll());
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        return copyOf(bankAccountRepository.findById(query.id())
                .map(List::of)
                .orElse(emptyList()));
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        return copyOf(bankAccountRepository.findByAccountHolder(query.accountHolder())
                .map(List::of)
                .orElse(emptyList()));
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        final List<BaseEntity> bankAccounts = switch (query.equalityType()) {
            case GREATER_THAN -> bankAccountRepository.findByBalanceGreaterThan(query.balance());
            case LESS_THAN -> bankAccountRepository.findByBalanceLessThan(query.balance());
        };
        return copyOf(bankAccounts);
    }
}
