package dev.pgjbz.cqrs.core.infrastructure;

import dev.pgjbz.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(final String aggregateId, final Iterable<BaseEvent> events, final int expectedVersion);
    List<BaseEvent> getEvent(final String aggregateId);
    List<String> getAggregateIds();
}
