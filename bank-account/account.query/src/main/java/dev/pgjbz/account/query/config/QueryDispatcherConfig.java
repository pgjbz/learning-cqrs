package dev.pgjbz.account.query.config;

import dev.pgjbz.account.query.api.queries.*;
import dev.pgjbz.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QueryDispatcherConfig {

    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    @PostConstruct
    void registerHandlers() {
        queryDispatcher.registryHandler(FindAllAccountsQuery.class, queryHandler::handle);
        queryDispatcher.registryHandler(FindAccountByIdQuery.class, queryHandler::handle);
        queryDispatcher.registryHandler(FindAccountByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registryHandler(FindAccountWithBalanceQuery.class, queryHandler::handle);
    }
}
