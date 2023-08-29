package dev.pgjbz.account.common.events;

import dev.pgjbz.cqrs.core.events.BaseEvent;
import lombok.Builder;

@Builder
public record AccountClosedEvent(String id, int version) implements BaseEvent {

    @Override
    public BaseEvent withVersion(int version) {
        return new AccountClosedEvent(id(), version);
    }
}