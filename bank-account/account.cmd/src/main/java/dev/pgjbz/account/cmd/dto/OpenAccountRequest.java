package dev.pgjbz.account.cmd.dto;

import dev.pgjbz.account.common.dto.AccountType;

import java.math.BigDecimal;

public record OpenAccountRequest(String accountHolder, AccountType accountType,
                                 BigDecimal openingBalance) {
}
