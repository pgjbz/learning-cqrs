package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.account.common.dto.AccountType;
import dev.pgjbz.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;

public record OpenAccountCommand(String id, String accountHolder, AccountType accountType,
                                 BigDecimal openingBalance) implements BaseCommand {
}