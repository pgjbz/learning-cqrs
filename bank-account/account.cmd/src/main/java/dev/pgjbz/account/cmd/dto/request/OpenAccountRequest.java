package dev.pgjbz.account.cmd.dto.request;

import dev.pgjbz.account.common.dto.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record OpenAccountRequest(@NotBlank String accountHolder, @NotNull AccountType accountType,
                                 @PositiveOrZero @NotNull BigDecimal openingBalance) {
}
