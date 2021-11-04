package com.gt.scr.movie.config;

import com.gt.scr.movie.audit.EventMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class AuditConfig {
    @Bean
    @Qualifier("EventSink")
    public Sinks.Many<EventMessage> multicast() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
