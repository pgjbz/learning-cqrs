package dev.pgjbz.account.query.api.queries;

import dev.pgjbz.cqrs.core.queries.BaseQuery;

public record FindAccountByHolderQuery(String accountHolder) implements BaseQuery {
}
