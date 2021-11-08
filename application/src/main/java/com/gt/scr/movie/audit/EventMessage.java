package com.gt.scr.movie.audit;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public interface EventMessage {
    UUID eventId();
    long creationTimestamp();
    EventType eventType();
}
