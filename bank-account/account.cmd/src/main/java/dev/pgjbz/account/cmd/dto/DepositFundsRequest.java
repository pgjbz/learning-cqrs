package dev.pgjbz.account.cmd.dto;

import dev.pgjbz.account.common.dto.BaseResponse;

import java.math.BigDecimal;

public record DepositFundsRequest(BigDecimal amount) {
}
