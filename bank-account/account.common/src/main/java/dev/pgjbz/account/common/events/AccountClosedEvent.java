package dev.pgjbz.account.common.events;

import dev.pgjbz.cqrs.core.events.BaseEvent;
import lombok.Builder;

@Builder
public record AccountClosedEvent(String id, int version) implements BaseEvent {
    
}