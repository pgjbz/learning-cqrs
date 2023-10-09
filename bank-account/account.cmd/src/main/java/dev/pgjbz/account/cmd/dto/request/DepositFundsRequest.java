package dev.pgjbz.account.cmd.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record DepositFundsRequest(@PositiveOrZero @NotNull BigDecimal amount) {
}
