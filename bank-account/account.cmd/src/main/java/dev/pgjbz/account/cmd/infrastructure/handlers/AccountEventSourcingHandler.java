package dev.pgjbz.account.cmd.infrastructure.handlers;

import dev.pgjbz.account.cmd.domain.aggregates.AccountAggregate;
import dev.pgjbz.cqrs.core.domain.AggregateRoot;
import dev.pgjbz.cqrs.core.events.BaseEvent;
import dev.pgjbz.cqrs.core.handlers.EventSourcingHandler;
import dev.pgjbz.cqrs.core.infrastructure.EventStore;
import dev.pgjbz.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

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

    @Override
    public void republishEvents() {
        final List<String> aggregateIds = eventStore.getAggregateIds();
        for(final String aggregateId: aggregateIds) {
            final AccountAggregate accountAggregate = getById(aggregateId);
            if(Objects.isNull(accountAggregate) || !accountAggregate.getActive()) continue;
            final List<BaseEvent> events = eventStore.getEvent(aggregateId);
            for(final BaseEvent event: events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
