package dev.pgjbz.account.query.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.pgjbz.account.common.dto.BaseResponse;
import dev.pgjbz.cqrs.core.domain.BaseEntity;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@JsonInclude(NON_NULL)
public record AccountLookupResponse(String message, List<BaseEntity> accounts) implements BaseResponse {
    public AccountLookupResponse(final String message) {
        this(message, null);
    }

    public AccountLookupResponse(final List<BaseEntity> accounts) {
        this(null, accounts);
    }
}
