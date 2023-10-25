package dev.pgjbz.account.query.infrastructure.dispatchers;

import dev.pgjbz.cqrs.core.domain.BaseEntity;
import dev.pgjbz.cqrs.core.infrastructure.QueryDispatcher;
import dev.pgjbz.cqrs.core.queries.BaseQuery;
import dev.pgjbz.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod<BaseQuery>>> routes = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseQuery> void registryHandler(Class<T> clazz, QueryHandlerMethod<T> handler) {
        final List<QueryHandlerMethod<BaseQuery>> handlers = routes.computeIfAbsent(clazz, c -> new LinkedList<>());
        handlers.add((QueryHandlerMethod<BaseQuery>)handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        final List<QueryHandlerMethod<BaseQuery>> handlers = routes.get(query.getClass());
        if(CollectionUtils.isEmpty(handlers))
            throw new RuntimeException("No query handler was registered!");

        if(handlers.size() > 1 )
            throw new RuntimeException("Cannot send query to more than one handler!");

        return (List<U>)handlers.get(0).handle(query);
    }
}
