package dev.pgjbz.cqrs.core.events;

import dev.pgjbz.cqrs.core.messages.Message;

public interface BaseEvent extends Message {
    int version();
}