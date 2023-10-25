package dev.pgjbz.account.query.api.queries;

import dev.pgjbz.cqrs.core.queries.BaseQuery;

public record FindAccountByIdQuery(String id) implements BaseQuery {
}
