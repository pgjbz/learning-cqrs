package dev.pgjbz.account.common.events;

import dev.pgjbz.cqrs.core.events.BaseEvent;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FundsWithdrawnEvent(String id, int version, BigDecimal amount) implements BaseEvent  {
    
}