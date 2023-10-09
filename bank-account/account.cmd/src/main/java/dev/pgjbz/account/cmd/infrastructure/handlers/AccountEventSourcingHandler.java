package dev.pgjbz.account.cmd.infrastructure.handlers;

import dev.pgjbz.account.cmd.domain.aggregates.AccountAggregate;
import dev.pgjbz.cqrs.core.domain.AggregateRoot;
import dev.pgjbz.cqrs.core.events.BaseEvent;
import dev.pgjbz.cqrs.core.handlers.EventSourcingHandler;
import dev.pgjbz.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(final AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(final String id) {
        final AccountAggregate aggregate = new AccountAggregate();
        final List<BaseEvent> events = eventStore.getEvent(id);
        if (!CollectionUtils.isEmpty(events)) {
            aggregate.replayEvents(events);
            events.stream()
                .map(BaseEvent::version)
                .max(Comparator.naturalOrder())
                .ifPresent(aggregate::setVersion);
        }
        return aggregate;
    }
}
