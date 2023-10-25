package dev.pgjbz.account.query.api.controllers.exceptions;

import dev.pgjbz.account.common.dto.BaseResponse;

public record StandardError(String message, int status) implements BaseResponse {
}
