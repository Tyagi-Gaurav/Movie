package com.gt.scr.movie.dao;

import com.gt.scr.movie.audit.UserEventMessage;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Mono<Void> save(UserEventMessage eventMessage);
}
