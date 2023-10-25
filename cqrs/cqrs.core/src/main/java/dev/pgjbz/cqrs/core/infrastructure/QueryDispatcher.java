package dev.pgjbz.cqrs.core.infrastructure;

import dev.pgjbz.cqrs.core.domain.BaseEntity;
import dev.pgjbz.cqrs.core.queries.BaseQuery;
import dev.pgjbz.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registryHandler(Class<T> clazz, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
