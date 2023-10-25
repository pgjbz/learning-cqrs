package dev.pgjbz.account.query.api.queries;

import dev.pgjbz.account.query.api.dto.EqualityType;
import dev.pgjbz.cqrs.core.queries.BaseQuery;

import java.math.BigDecimal;

public record FindAccountWithBalanceQuery(EqualityType equalityType, BigDecimal balance) implements BaseQuery {
}
