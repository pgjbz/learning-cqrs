package dev.pgjbz.account.cmd.config;


import dev.pgjbz.account.cmd.api.commands.*;
import dev.pgjbz.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommandDispatcherConfig {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    @PostConstruct
    public void registerHandlers() {
        log.info("registering handlers");
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
        log.info("handlers registered successfully");
    }

}
