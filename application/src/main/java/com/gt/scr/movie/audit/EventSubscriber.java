package com.gt.scr.movie.audit;

import com.gt.scr.movie.dao.EventRepository;
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
    private final EventRepository eventRepository;

    public EventSubscriber(EventRepository eventRepository,
                           @Qualifier("EventSink") Sinks.Many<EventMessage> eventSink) {
        this.eventRepository = eventRepository;
        eventSink.asFlux().flatMap(this::accept).subscribe();
    }

    private <R> Publisher<? extends R> accept(EventMessage eventMessage) {
        LOG.info("Event Received: {}", eventMessage);
        eventRepository.save(eventMessage);
        return Flux.empty();
    }
}
