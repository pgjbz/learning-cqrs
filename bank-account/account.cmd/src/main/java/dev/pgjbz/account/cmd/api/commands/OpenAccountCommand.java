package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;

public record OpenAccountCommand(String id, String accountHolder, String AccountType,
                                 BigDecimal openingBalance) implements BaseCommand {
}