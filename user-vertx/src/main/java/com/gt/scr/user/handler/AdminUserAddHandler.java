package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.utils.DataEncoder;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class AdminUserAddHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userService;
    private final DataEncoder dataEncoder;

    public AdminUserAddHandler(UserServiceV2 userService,
                               DataEncoder dataEncoder) {
        this.userService = userService;
        this.dataEncoder = dataEncoder;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        AccountCreateRequestDTO accountCreateRequestDTO =
                routingContext.getBodyAsJson().mapTo(AccountCreateRequestDTO.class);

        userService.add(new User(UUID.randomUUID(),
                        accountCreateRequestDTO.firstName(),
                        accountCreateRequestDTO.lastName(),
                        accountCreateRequestDTO.userName(),
                        encode(accountCreateRequestDTO.password()),
                        Collections.singletonList(accountCreateRequestDTO.role())))
                .onFailure(throwable -> {
                    if (throwable instanceof ReplyException) {
                        routingContext.response().setStatusCode(403);
                        routingContext.response().end();
                    }
                }).onSuccess(event -> {
                    routingContext.response().setStatusCode(204);
                    routingContext.response().end();
                });
    }

    private String encode(String password) {
        try {
            return dataEncoder.encode(password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
