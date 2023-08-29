package dev.pgjbz.cqrs.core.events;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Document(collation = "eventStore")
public record EventModel(
        @Id
        String id,
        LocalDateTime timeStamp,
        String aggregateIdentifier,
        String aggregateType,
        int version,
        String eventType,
        BaseEvent eventData
) {

}
