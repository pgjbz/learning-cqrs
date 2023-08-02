package dev.pgjbz.account.cmd.api.commands;


import dev.pgjbz.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;

public record WithdrawFundsCommand(String id, BigDecimal amount) implements BaseCommand {
}