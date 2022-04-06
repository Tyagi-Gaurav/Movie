package com.gt.scr.user.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.ClassUtils;

import java.util.Set;

public class ConfigPublishHandler implements Handler<RoutingContext> {
    private static final Set<String> SENSITIVE_FIELDS = Set.of("db.password", "auth.token.key");
    private final JsonObject config;

    public ConfigPublishHandler(JsonObject config) {
        this.config = config;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.response().setChunked(true);
        routingContext.response().setStatusCode(200);
        routingContext.response().write(hideSensitiveFields(config).toBuffer());
        routingContext.response().end();
    }

    public JsonObject hideSensitiveFields(JsonObject jsonObject) {
        JsonObject parentObject = new JsonObject();
        jsonObject.stream()
                .forEach(entry -> {
                    if (SENSITIVE_FIELDS.contains(entry.getKey())) {
                        parentObject.put(entry.getKey(), "******");
                    } else {
                        Object value = entry.getValue();
                        if (value instanceof String || ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                            parentObject.put(entry.getKey(), value);
                        } else {
                            parentObject.put(entry.getKey(), hideSensitiveFields(new JsonObject(value.toString())));
                        }
                    }
                });

        return parentObject;
    }
}
