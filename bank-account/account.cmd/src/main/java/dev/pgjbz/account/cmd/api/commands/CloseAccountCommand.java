package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.cqrs.core.commands.BaseCommand;

public record CloseAccountCommand(String id) implements BaseCommand {
}