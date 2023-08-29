package dev.pgjbz.account.cmd.domain;

import dev.pgjbz.cqrs.core.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateIdentifier(final String aggregateIdentifier);
}
