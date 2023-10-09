package dev.pgjbz.account.cmd.api.commands;

import dev.pgjbz.account.cmd.dto.request.OpenAccountRequest;
import dev.pgjbz.account.common.dto.AccountType;
import dev.pgjbz.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;
import java.util.UUID;

public record OpenAccountCommand(String id, String accountHolder, AccountType accountType,
                                 BigDecimal openingBalance) implements BaseCommand {
    public OpenAccountCommand(OpenAccountRequest openAccountRequest) {
        this(UUID.randomUUID().toString(),
                openAccountRequest.accountHolder(),
                openAccountRequest.accountType(),
                openAccountRequest.openingBalance());
    }
}