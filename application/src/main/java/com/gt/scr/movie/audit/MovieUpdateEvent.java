package com.gt.scr.movie.audit;

import java.math.BigDecimal;
import java.util.UUID;

public record MovieUpdateEvent(
        UUID eventId,
        UUID ownerUser,
        UUID originatorUser,
        EventType eventType,
        long creationTimestamp,
        String name,
        int yearProduced,
        BigDecimal rating) implements UserEventMessage {

    public MovieUpdateEvent(UUID ownerUser,
                            UUID originatorUser,
                            String name,
                            int yearProduced,
                            BigDecimal rating) {
        this(UUID.randomUUID(),
                ownerUser,
                originatorUser,
                EventType.MOVIE_CREATE,
                System.nanoTime(),
                name,
                yearProduced,
                rating);
    }
}
