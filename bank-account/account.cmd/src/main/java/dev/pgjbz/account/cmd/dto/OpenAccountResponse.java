package dev.pgjbz.account.cmd.dto;

import dev.pgjbz.account.common.dto.BaseResponse;

public record OpenAccountResponse(String id, String message)
        implements BaseResponse {
}
