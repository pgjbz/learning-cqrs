package dev.pgjbz.account.common.events;

import dev.pgjbz.cqrs.core.events.BaseEvent;

public record AccountClosedEvent(String id, int version) implements BaseEvent {
    
}