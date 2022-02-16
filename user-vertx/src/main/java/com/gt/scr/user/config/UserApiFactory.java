package com.gt.scr.user.config;

import com.gt.scr.user.handler.AccountResourceHandler;
import com.gt.scr.user.handler.AuthenticationHandler;
import com.gt.scr.user.handler.SecurityHandler;
import com.gt.scr.user.handler.UserGetByIdHandler;
import com.gt.scr.user.handler.UserGetByNameHandler;
import com.gt.scr.user.handler.ValidationHandler;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.service.domain.Role;
import io.micronaut.context.annotation.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import javax.validation.Validator;

import static com.gt.scr.user.handler.SecurityHandler.ALLOWED_ROLES;

@Factory
public class UserApiFactory {

    @Inject
    AuthenticationHandler authenticationHandler;

    @Inject
    AccountResourceHandler accountResourceHandler;

    @Inject
    SecurityHandler securityHandler;

    @Inject
    Validator validator;

    @Inject
    UserGetByNameHandler userGetByNameHandler;

    @Inject
    Vertx vertx;

    @Inject
    UserGetByIdHandler userGetByIdHandler;

    @Singleton
    @Named("userApiRouter")
    public Router userApiRouter() {
        Router userApiRouter = Router.router(vertx);

        userApiRouter.post("/account/create")
                .consumes("application/vnd+account.create.v1+json")
                .produces("application/vnd+account.create.v1+json")
                .handler(BodyHandler.create())
                .handler(new ValidationHandler<>(validator, AccountCreateRequestDTO.class))
                .handler(accountResourceHandler);

        userApiRouter.post("/login")
                .consumes("application/vnd.login.v1+json")
                .produces("application/vnd.login.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .handler(BodyHandler.create())
                .handler(authenticationHandler);

        userApiRouter.get()
                .consumes("application/vnd.user.fetchByUserName.v1+json")
                .produces("application/vnd.user.fetchByUserName.v1+json")
                .putMetadata(ALLOWED_ROLES, Role.ALL)
                .handler(ResponseContentTypeHandler.create())
                .handler(securityHandler)
                .handler(userGetByNameHandler);

        userApiRouter.get()
                .consumes("application/vnd.user.fetchByUserId.v1+json")
                .produces("application/vnd.user.fetchByUserId.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .handler(userGetByIdHandler);

        return userApiRouter;
    }
}
