package dev.pgjbz.cqrs.core.producers;

import dev.pgjbz.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(final String topicName, final BaseEvent baseEvent);
}
