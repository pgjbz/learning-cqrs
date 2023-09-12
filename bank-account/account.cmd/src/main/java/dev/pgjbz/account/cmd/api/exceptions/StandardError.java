package dev.pgjbz.account.cmd.api.exceptions;

import dev.pgjbz.account.common.dto.BaseResponse;

public record StandardError(String message, int status) implements BaseResponse {
}
