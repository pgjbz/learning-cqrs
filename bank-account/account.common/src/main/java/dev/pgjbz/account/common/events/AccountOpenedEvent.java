package dev.pgjbz.account.common.events;

import dev.pgjbz.account.common.dto.AccountType;
import dev.pgjbz.cqrs.core.events.BaseEvent;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountOpenedEvent(String id, int version, String accountHolder, AccountType accountType,
                                 LocalDateTime createdDate, BigDecimal openingBalance) implements BaseEvent {

}