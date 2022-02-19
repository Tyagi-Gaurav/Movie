package com.gt.scr.user.exception;

import io.vertx.ext.web.RoutingContext;

public class ExceptionMapper {
    public void mapException(RoutingContext routingContext,
                             Throwable throwable,
                             Class<?> clazz,
                             int statusCode, int defaultStatusCode) {
        if (throwable.getClass() == clazz) {
            routingContext.response().setStatusCode(statusCode).end();
        } else {
            routingContext.response().setStatusCode(defaultStatusCode).end();
        }
    }

    public void mapException(RoutingContext routingContext, int statusCode) {
        routingContext.response().setStatusCode(statusCode).end();
    }
}
