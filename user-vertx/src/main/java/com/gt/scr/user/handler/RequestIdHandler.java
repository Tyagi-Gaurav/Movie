package com.gt.scr.user.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class RequestIdHandler implements Handler<RoutingContext> {
    private static final String REQUEST_ID = "requestId";
    private static final Logger LOG = LoggerFactory.getLogger(RequestIdHandler.class);

    @Override
    public void handle(RoutingContext ctx) {
        long start = System.nanoTime();
        ctx.addHeadersEndHandler(v -> {
            Duration duration = Duration.ofNanos(System.nanoTime() - start);
            LOG.info("Request Duration {}ms", duration.toMillis());

            String requestId = ctx.request().getHeader(REQUEST_ID);
            if (StringUtils.isEmpty(requestId)) {
                ctx.response().putHeader(REQUEST_ID, UUID.randomUUID().toString());
            } else {
                ctx.response().putHeader(REQUEST_ID, requestId);
            }
        });

        ctx.next();
    }
}
