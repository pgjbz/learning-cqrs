package dev.pgjbz.cqrs.core.infrastructure;

import dev.pgjbz.cqrs.core.commands.BaseCommand;
import dev.pgjbz.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(final Class<T> type, final CommandHandlerMethod<T> handler);
    void send(final BaseCommand command);
}