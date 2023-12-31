package dev.pgjbz.account.cmd.infrastructure.stores;

import dev.pgjbz.account.cmd.domain.repository.EventStoreRepository;
import dev.pgjbz.account.cmd.infrastructure.exceptions.EmptyEventStoreException;
import dev.pgjbz.cqrs.core.events.BaseEvent;
import dev.pgjbz.cqrs.core.events.EventModel;
import dev.pgjbz.cqrs.core.exceptions.AggregateNotFoundException;
import dev.pgjbz.cqrs.core.exceptions.ConcurrencyException;
import dev.pgjbz.cqrs.core.infrastructure.EventStore;
import dev.pgjbz.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvents(final String aggregateId, final Iterable<BaseEvent> events, final int expectedVersion) {
        final List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        log.info("expected version for aggregateId '{}' is '{}'", aggregateId, expectedVersion);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() - 1).version() != expectedVersion)
            throw new ConcurrencyException();
        int version = expectedVersion;
        for(final BaseEvent event: events){
            version++;
            final BaseEvent newEvent = event.withVersion(version);
            final var eventModel = EventModel.builder()
                    .timeStamp(LocalDateTime.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(event.getClass().getTypeName())
                    .eventData(newEvent)
                    .version(version)
                    .build();
            final EventModel persistedEvent = eventStoreRepository.save(eventModel);
            if(hasLength(persistedEvent.id())) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvent(final String aggregateId) {
        final List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(CollectionUtils.isEmpty(eventStream))
            throw new AggregateNotFoundException("incorrect account ID provided!");
        return eventStream.stream().map(EventModel::eventData).toList();
    }

    @Override
    public List<String> getAggregateIds() {
        final List<EventModel> eventStream = eventStoreRepository.findAll();
        if(CollectionUtils.isEmpty(eventStream)) {
            throw new EmptyEventStoreException();
        }
        return eventStream.stream().map(EventModel::aggregateIdentifier).toList();
    }
}
