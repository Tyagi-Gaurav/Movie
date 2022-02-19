package com.gt.scr.user.config;

import com.gt.scr.user.handler.AdminUserAddHandler;
import com.gt.scr.user.handler.AdminUserGetUsersHandler;
import com.gt.scr.user.handler.SecurityHandler;
import com.gt.scr.user.handler.UserDeleteHandler;
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
public class AdminApiFactory {
    @Inject
    Vertx vertx;

    @Inject
    SecurityHandler securityHandler;

    @Inject
    UserDeleteHandler userDeleteHandler;

    @Inject
    AdminUserAddHandler adminUserAddHandler;

    @Inject
    AdminUserGetUsersHandler adminUserGetUsersHandler;

    @Inject
    Validator validator;

    @Singleton
    @Named("adminRoute")
    public Router adminRoute() {
        Router adminUserManageAPI = Router.router(vertx);

        adminUserManageAPI.delete()
                .consumes("application/vnd.user.delete.v1+json")
                .produces("application/vnd.user.delete.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .putMetadata(ALLOWED_ROLES, Role.ADMIN)
                .handler(securityHandler)
                .handler(userDeleteHandler);

        adminUserManageAPI.post()
                .consumes("application/vnd.user.add.v1+json")
                .produces("application/vnd.user.add.v1+json")
                .handler(BodyHandler.create())
                .putMetadata(ALLOWED_ROLES, Role.ADMIN)
                .handler(securityHandler)
                .handler(new ValidationHandler<>(validator, AccountCreateRequestDTO.class))
                .handler(adminUserAddHandler);

        adminUserManageAPI.get()
                .consumes("application/vnd.user.read.v1+json")
                .produces("application/vnd.user.read.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .putMetadata(ALLOWED_ROLES, Role.ADMIN)
                .handler(securityHandler)
                .handler(adminUserGetUsersHandler);

        return adminUserManageAPI;
    }
 }
