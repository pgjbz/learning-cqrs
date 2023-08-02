package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;

public record DepositFundsCommand(String id, BigDecimal amount) implements BaseCommand {

}