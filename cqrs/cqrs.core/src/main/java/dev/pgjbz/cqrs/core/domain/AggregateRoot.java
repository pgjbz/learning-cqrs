package dev.pgjbz.cqrs.core.domain;

import dev.pgjbz.cqrs.core.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public abstract class AggregateRoot {

    protected String id;
    @Setter
    private int version = -1;
    private final List<BaseEvent> uncommittedChanges = new ArrayList<>();

    public void markChangesAsCommitted() {
        this.uncommittedChanges.clear();
    }

    protected void applyChange(final BaseEvent event, final boolean isNewEvent) {
        try {
            final Method method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException ex) {
            log.warn("the apply method was not found in the aggregate for {}", event.getClass().getName());
        } catch (Exception e) {
            log.error("error apply event to aggregate", e);
        } finally {
            if(isNewEvent) {
                uncommittedChanges.add(event);
            }
        }
    }

    public void raiseEvent(final BaseEvent event) {
        applyChange(event, true);
    }
    public void replayEvents(final Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }

}
