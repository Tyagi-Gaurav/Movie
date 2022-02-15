package com.gt.scr.user.handler;

import com.gt.scr.user.service.UserServiceV2;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class UserDeleteHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userServiceV2;

    public UserDeleteHandler(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        String userId = routingContext.request().getParam("userId");

        userServiceV2.delete(UUID.fromString(userId))
                .onFailure(throwable -> {
                    routingContext.response().setStatusCode(500);
                    routingContext.response().end();
                })
                .onSuccess(jsonObject -> {
                    routingContext.response().setStatusCode(200);
                    routingContext.response().end();
                });
    }
}
