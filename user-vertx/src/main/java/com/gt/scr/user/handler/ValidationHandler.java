package com.gt.scr.user.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationHandler<T> implements Handler<RoutingContext>  {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationHandler.class);
    private final Validator validator;
    private final Class<T> clazz;

    public ValidationHandler(Validator validator,
                             Class<T> accountCreateRequestDTOClass) {
        this.validator = validator;
        this.clazz = accountCreateRequestDTOClass;
    }

    @Override
    public void handle(RoutingContext event) {
        LOG.debug("Start validation");
        Set<ConstraintViolation<T>> validate =
                validator.validate(event.body().asPojo(clazz));
        LOG.debug("Validation Result {}", validate);

        if (!validate.isEmpty()) {
            event.response().setStatusCode(400).end();
        }

        event.next();
    }
}
