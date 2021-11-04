package com.gt.scr.movie.audit;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class EventSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(EventSubscriber.class);

    public EventSubscriber(@Qualifier("EventSink") Sinks.Many<EventMessage> eventSink) {
        eventSink.asFlux().flatMap(this::accept).subscribe();
    }

    private <R> Publisher<? extends R> accept(EventMessage eventMessage) {
        LOG.info("Event Received: {}", eventMessage);
        return Flux.empty();
    }


}
