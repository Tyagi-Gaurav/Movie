package com.gt.scr.movie.audit;

import com.gt.scr.movie.dao.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventSubscriberTest {
    @Mock
    private EventRepository eventRepository;

    private Sinks.Many<UserEventMessage> testSink = Sinks.many().unicast().onBackpressureBuffer();

    @Test
    void shouldSaveEventToAuditRepository() {
        new EventSubscriber(eventRepository, testSink);
        //when
        testSink.tryEmitNext(new MovieCreateEvent(UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(),
                "movieName", 2010,
                BigDecimal.valueOf(7)));

        verify(eventRepository).save(any(UserEventMessage.class));
    }
}