package dev.pgjbz.account.cmd.infrastructure;

import dev.pgjbz.cqrs.core.commands.BaseCommand;
import dev.pgjbz.cqrs.core.commands.CommandHandlerMethod;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod handler) {
        final var handles = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handles.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        final var handlers = routes.get(command.getClass());
        if(CollectionUtils.isEmpty(handlers))
            throw new RuntimeException("No command handler was registered");
        if(handlers.size() > 1)
            throw new RuntimeException("Cannot send command to more than one handler");
        handlers.get(0).handler(command);
    }

}