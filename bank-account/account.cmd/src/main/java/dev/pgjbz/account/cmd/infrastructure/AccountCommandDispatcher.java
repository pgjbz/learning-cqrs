package dev.pgjbz.account.cmd.infrastructure;

import dev.pgjbz.cqrs.core.commands.BaseCommand;
import dev.pgjbz.cqrs.core.commands.CommandHandlerMethod;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod<BaseCommand>>> routes = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseCommand> void registerHandler(final Class<T> type, CommandHandlerMethod<T> handler) {
        final List<CommandHandlerMethod<BaseCommand>> handles = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handles.add((CommandHandlerMethod<BaseCommand>)handler);
    }

    @Override
    public void send(final BaseCommand command) {
        final List<CommandHandlerMethod<BaseCommand>> handlers = routes.get(command.getClass());
        if(CollectionUtils.isEmpty(handlers))
            throw new RuntimeException("No command handler was registered");
        if(handlers.size() > 1)
            throw new RuntimeException("Cannot send command to more than one handler");
        handlers.get(0).handler(command);
    }

}