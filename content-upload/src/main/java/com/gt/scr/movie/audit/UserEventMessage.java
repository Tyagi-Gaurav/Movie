package com.gt.scr.movie.audit;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = @JsonSubTypes.Type(value = MovieCreateEvent.class, name = "movieCreateEvent"))
public interface UserEventMessage {
    UUID eventId();
    UUID ownerUser();
    UUID originatorUser();
    long creationTimestamp();
    EventType eventType();
}
