package dev.pgjbz.account.common.events;

import dev.pgjbz.cqrs.core.events.BaseEvent;

import java.math.BigDecimal;

public record FundsWithdrawnEvent(String id, int version, BigDecimal amount) implements BaseEvent  {
    
}