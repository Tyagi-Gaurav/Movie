package com.gt.scr.movie.audit;

import com.gt.scr.movie.dao.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventSubscriberTest {
    @Mock
    private EventRepository eventRepository;

    private EventSubscriber eventSubscriber;
    private Sinks.Many<EventMessage> testSink = Sinks.many().unicast().onBackpressureBuffer();

    @BeforeEach
    void setUp() {
         eventSubscriber = new EventSubscriber(eventRepository, testSink);
    }

    @Test
    void shouldSaveEventToAuditRepository() {
        //when
        testSink.tryEmitNext(new MovieCreateEvent("movieName", 2010,
                        BigDecimal.valueOf(7)));

        verify(eventRepository).save(any(EventMessage.class));
    }
}