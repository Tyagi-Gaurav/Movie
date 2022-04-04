package com.gt.scr.user.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessibleEndpointHandler implements Handler<RoutingContext> {
    private final Map<String, Boolean> endpointsMap;
    private final Map<String, Boolean> endpointsRegexMap;

    public AccessibleEndpointHandler(Vertx vertx) {
        JsonObject config = vertx.getOrCreateContext().config();
        endpointsMap = config.getJsonObject("accessible").getJsonObject("endpoints").mapTo(Map.class);
        endpointsRegexMap = config.getJsonObject("accessible").getJsonObject("endpointsRegex").mapTo(Map.class);
    }

    @Override
    public void handle(RoutingContext context) {
        HttpMethod method = context.request().method();
        String path = context.request().path();

        String pathToCheck = String.format("%s-%s", method, path);

        if (!(isEnabled(pathToCheck) || satisfiesRegex(pathToCheck))) {
            context.response().setStatusCode(404);
            context.response().end();
        } else {
            context.next();
        }
    }

    public boolean isEnabled(String pathToCheck) {
        return endpointsMap.getOrDefault(pathToCheck, false);
    }

    public boolean satisfiesRegex(String pathToCheck) {
        return endpointsRegexMap.keySet().stream().map(Pattern::compile)
                .map(p -> p.matcher(pathToCheck))
                .anyMatch(Matcher::matches);
    }
}
