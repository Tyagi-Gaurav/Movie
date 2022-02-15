package com.gt.scr.user;

import com.gt.scr.user.handler.AccountResourceHandler;
import com.gt.scr.user.handler.AdminUserAddHandler;
import com.gt.scr.user.handler.AdminUserGetUsersHandler;
import com.gt.scr.user.handler.AuthenticationHandler;
import com.gt.scr.user.handler.SecurityHandler;
import com.gt.scr.user.handler.UserDeleteHandler;
import com.gt.scr.user.handler.UserGetByIdHandler;
import com.gt.scr.user.handler.UserGetByNameHandler;
import com.gt.scr.user.handler.ValidationHandler;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.service.domain.Role;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ResponseContentTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

import static com.gt.scr.user.handler.SecurityHandler.ALLOWED_ROLES;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(HttpServerVerticle.class);

    private final AccountResourceHandler accountResourceHandler;
    private final AuthenticationHandler authenticationHandler;
    private final Validator validator;
    private final AdminUserAddHandler adminUserAddHandler;
    private final AdminUserGetUsersHandler adminUserGetUsersHandler;
    private final SecurityHandler securityHandler;
    private final UserGetByNameHandler userGetByNameHandler;
    private final UserDeleteHandler userDeleteHandler;
    private final UserGetByIdHandler userGetByIdHandler;

    public HttpServerVerticle(AccountResourceHandler accountResourceHandler,
                              AuthenticationHandler authenticationHandler,
                              Validator validator,
                              AdminUserAddHandler adminUserAddHandler,
                              AdminUserGetUsersHandler adminUserGetUsersHandler,
                              SecurityHandler securityHandler,
                              UserGetByNameHandler userGetByNameHandler,
                              UserDeleteHandler userDeleteHandler,
                              UserGetByIdHandler userGetByIdHandler) {
        this.accountResourceHandler = accountResourceHandler;
        this.authenticationHandler = authenticationHandler;
        this.validator = validator;
        this.adminUserAddHandler = adminUserAddHandler;
        this.adminUserGetUsersHandler = adminUserGetUsersHandler;
        this.securityHandler = securityHandler;
        this.userGetByNameHandler = userGetByNameHandler;
        this.userDeleteHandler = userDeleteHandler;
        this.userGetByIdHandler = userGetByIdHandler;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer httpServer = vertx.createHttpServer();
        int port = config().getInteger("http.port", 6060);

        Router mainRouter = Router.router(vertx);
        Router restAPI = Router.router(vertx);
        Router adminUserManageAPI = Router.router(vertx);

        restAPI.post("/account/create")
                .consumes("application/vnd+account.create.v1+json")
                .produces("application/vnd+account.create.v1+json")
                .handler(BodyHandler.create())
                .handler(new ValidationHandler<>(validator, AccountCreateRequestDTO.class))
                .handler(accountResourceHandler);

        restAPI.post("/login")
                .consumes("application/vnd.login.v1+json")
                .produces("application/vnd.login.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .handler(BodyHandler.create())
                .handler(authenticationHandler);

        restAPI.get()
                .consumes("application/vnd.user.fetchByUserName.v1+json")
                .produces("application/vnd.user.fetchByUserName.v1+json")
                .putMetadata(ALLOWED_ROLES, Role.ALL)
                .handler(ResponseContentTypeHandler.create())
                .handler(securityHandler)
                .handler(userGetByNameHandler);

        restAPI.get()
                .consumes("application/vnd.user.fetchByUserId.v1+json")
                .produces("application/vnd.user.fetchByUserId.v1+json")
                .handler(ResponseContentTypeHandler.create())
                .handler(userGetByIdHandler);

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

        mainRouter.mountSubRouter("/user", restAPI);
        mainRouter.mountSubRouter("/user/manage", adminUserManageAPI);

        httpServer.requestHandler(mainRouter).listen(port, ar -> {
            if (ar.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });

        LOG.info("Open for requests on http://localhost:{}", port);
    }
}
