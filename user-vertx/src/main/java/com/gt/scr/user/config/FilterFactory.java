package com.gt.scr.user.config;

import com.gt.scr.user.handler.RequestIdHandler;
import io.micronaut.context.annotation.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class FilterFactory {
    private static final Logger LOG = LoggerFactory.getLogger(FilterFactory.class);

    @Inject
    Vertx vertx;

    @Singleton
    public RequestIdHandler requestIdHandler() {
        return new RequestIdHandler();
    }

    @Singleton
    @Named("webFilters")
    public Router webFilters(RequestIdHandler requestIdHandler) {
        Router filterRouter = Router.router(vertx);

        filterRouter.get().handler(h -> {
            LOG.info("Received GET request {}", h.request().path());
            h.next();
        }).handler(requestIdHandler);

        filterRouter.post().handler(h -> {
            LOG.info("Received POST request {}", h.request().path());
            h.next();
        }).handler(requestIdHandler);

        filterRouter.delete().handler(h -> {
            LOG.info("Received DELETE request {}", h.request().path());
            h.next();
        }).handler(requestIdHandler);

        filterRouter.put().handler(h -> {
            LOG.info("Received PUT request {}", h.request().path());
            h.next();
        }).handler(requestIdHandler);

        return filterRouter;
    }
}