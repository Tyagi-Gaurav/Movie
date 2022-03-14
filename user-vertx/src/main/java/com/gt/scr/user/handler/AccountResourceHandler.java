package com.gt.scr.user.handler;

import com.gt.scr.domain.User;
import com.gt.scr.user.exception.ExceptionMapper;
import com.gt.scr.user.exception.SystemException;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.user.service.domain.Role;
import com.gt.scr.utils.DataEncoder;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class AccountResourceHandler implements Handler<RoutingContext> {
    private final UserServiceV2 userService;
    private final DataEncoder dataEncoder;
    private final ExceptionMapper exceptionMapper;

    private static final Logger LOG = LoggerFactory.getLogger(AccountResourceHandler.class);

    public AccountResourceHandler(UserServiceV2 userService,
                                  DataEncoder dataEncoder,
                                  ExceptionMapper exceptionMapper) {
        this.userService = userService;
        this.dataEncoder = dataEncoder;
        this.exceptionMapper = exceptionMapper;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        AccountCreateRequestDTO accountCreateRequestDTO =
                routingContext.getBodyAsJson().mapTo(AccountCreateRequestDTO.class);

        if (Role.ADMIN.toString().equals(accountCreateRequestDTO.role())) {
            routingContext.response().setStatusCode(403);
            routingContext.response().end();
        } else {
            try {
                userService.add(new User(UUID.randomUUID(),
                                accountCreateRequestDTO.firstName(),
                                accountCreateRequestDTO.lastName(),
                                accountCreateRequestDTO.userName(),
                                encode(accountCreateRequestDTO.password()),
                                accountCreateRequestDTO.dateOfBirth(),
                                accountCreateRequestDTO.gender(),
                                accountCreateRequestDTO.homeCountry(),
                                Collections.singletonList(accountCreateRequestDTO.role())))
                        .onFailure(throwable ->
                                exceptionMapper.mapException(routingContext, throwable, ReplyException.class, 403, 500)
                        ).onSuccess(event -> routingContext.response().setStatusCode(204).end());
            } catch(Exception e) {
                LOG.error(e.getMessage(), e);
                routingContext.response().setStatusCode(500).end();
            }
        }
    }

    private String encode(String password) {
        try {
            return dataEncoder.encode(password);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
