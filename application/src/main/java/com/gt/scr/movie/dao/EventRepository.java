package com.gt.scr.movie.dao;

import com.gt.scr.movie.audit.EventMessage;
import reactor.core.publisher.Mono;

public interface EventRepository {
    Mono<Void> save(EventMessage eventMessage);
}
