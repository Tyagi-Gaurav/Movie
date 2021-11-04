package com.gt.scr.movie.audit;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventMessage(UUID eventId,
                           LocalDateTime creationTimeStamp,
                           EventType eventType,
                           MovieCreateEvent eventData) {
    public EventMessage(EventType eventType,
                 MovieCreateEvent eventData) {
        this(UUID.randomUUID(), LocalDateTime.now(), eventType, eventData);
    }
}
