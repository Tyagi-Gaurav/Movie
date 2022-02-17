package com.gt.scr.user.config;

import com.gt.scr.user.exception.ExceptionMapper;
import com.gt.scr.user.handler.AccountResourceHandler;
import com.gt.scr.user.handler.AdminUserAddHandler;
import com.gt.scr.user.handler.AdminUserGetUsersHandler;
import com.gt.scr.user.handler.AuthenticationHandler;
import com.gt.scr.user.handler.SecurityHandler;
import com.gt.scr.user.handler.UserDeleteHandler;
import com.gt.scr.user.handler.UserGetByIdHandler;
import com.gt.scr.user.handler.UserGetByNameHandler;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.user.service.UserServiceVertxImpl;
import com.gt.scr.utils.DataEncoder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micronaut.context.annotation.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.healthchecks.Status;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bouncycastle.util.encoders.HexEncoder;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Factory
public class BeanFactory {
    @Inject
    Vertx vertx;

    @Singleton
    public DataEncoder dataEncoder() {
        return new DataEncoder(new HexEncoder());
    }

    @Singleton
    public UserServiceV2 userService() {
        return new UserServiceVertxImpl(vertx);
    }

    @Singleton
    public UserGetByNameHandler userGetByNameHandler(UserServiceV2 userServiceV2,
                                                     ExceptionMapper exceptionMapper) {
        return new UserGetByNameHandler(userServiceV2, exceptionMapper);
    }

    @Singleton
    public UserGetByIdHandler userGetByIdHandler(UserServiceV2 userServiceV2,
                                                 ExceptionMapper exceptionMapper) {
        return new UserGetByIdHandler(userServiceV2, exceptionMapper);
    }

    @Singleton
    public UserDeleteHandler userDeleteHandler(UserServiceV2 userServiceV2) {
        return new UserDeleteHandler(userServiceV2);
    }

    @Singleton
    public AdminUserGetUsersHandler adminUserGetUsersHandler(UserServiceV2 userServiceV2) {
        return new AdminUserGetUsersHandler(userServiceV2);
    }

    @Singleton
    public SecurityHandler securityHandler(UserServiceV2 userServiceV2,
                                           Key signingKey) {
        return new SecurityHandler(userServiceV2, signingKey);
    }

    @Singleton
    public ExceptionMapper exceptionMapper() {
        return new ExceptionMapper();
    }

    @Singleton
    public AccountResourceHandler accountResourceHandler(UserServiceV2 userService,
                                                         DataEncoder dataEncoder,
                                                         ExceptionMapper exceptionMapper) {
        return new AccountResourceHandler(userService, dataEncoder, exceptionMapper);
    }

    @Singleton
    public AdminUserAddHandler adminUserAddHandler(UserServiceV2 userService,
                                                   DataEncoder dataEncoder) {
        return new AdminUserAddHandler(userService, dataEncoder);
    }

    @Singleton
    public Validator validationHandler() {
        return Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }

    @Singleton
    public Key signingKey() {
        String secret = vertx.getOrCreateContext().config().getString("auth.token.key");
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    @Singleton
    public AuthenticationHandler authenticationHandler(UserServiceV2 userServiceV2,
                                                       DataEncoder dataEncoder,
                                                       Key signingKey) {
        return new AuthenticationHandler(userServiceV2, dataEncoder, signingKey);
    }

    @Singleton
    public HealthCheckHandler healthCheckHandler() {
        HealthChecks healthChecks = HealthChecks.create(vertx);
        healthChecks.register("status", promise -> promise.complete(Status.OK()));
        return HealthCheckHandler.createWithHealthChecks(healthChecks);
    }
}
