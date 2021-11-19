package com.gt.scr.movie.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class MovieVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(MovieVerticle.class);
    private AtomicLong counter = new AtomicLong(1);

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.setPeriodic(5000, id -> {
            LOG.info("Tick");
        });

        vertx.createHttpServer()
                .requestHandler(req -> {
                    LOG.info("Request #{} from {}", counter.getAndIncrement(),
                            req.remoteAddress().host());
                    req.response().end("Hello!");
                }).listen(8080, ar -> {
           if (ar.succeeded()) {
               LOG.info("Movie Verticle started");
           } else {
               LOG.error("Failed to start Http Server", ar.cause());
           }
        });
        LOG.info("Open http://localhost:8080");
    }
}
