package dev.pgjbz.cqrs.core.handlers;

import dev.pgjbz.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(final AggregateRoot aggregate);
    T getById(final String id);
}
