package dev.pgjbz.account.query.domain;

import dev.pgjbz.cqrs.core.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountHolder(final String accountHolder);
    List<BaseEntity> findByBalanceGreaterThan(final BigDecimal balance);
    List<BaseEntity> findByBalanceLessThan(final BigDecimal balance);

}