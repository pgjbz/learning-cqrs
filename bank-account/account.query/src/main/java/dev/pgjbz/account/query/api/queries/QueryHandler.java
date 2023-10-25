package dev.pgjbz.account.query.api.queries;

import dev.pgjbz.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(final FindAllAccountsQuery query);
    List<BaseEntity> handle(final FindAccountByIdQuery query);
    List<BaseEntity> handle(final FindAccountByHolderQuery query);
    List<BaseEntity> handle(final FindAccountWithBalanceQuery query);
}
