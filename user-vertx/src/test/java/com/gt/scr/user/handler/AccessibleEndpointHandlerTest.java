package com.gt.scr.user.handler;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(VertxExtension.class)
class AccessibleEndpointHandlerTest {
    private final RoutingContext routingContext = Mockito.mock(RoutingContext.class, Mockito.RETURNS_DEEP_STUBS);

    private AccessibleEndpointHandler accessibleEndpointHandler;

    @BeforeEach
    void setUp(Vertx vertx) {
        MockitoAnnotations.openMocks(routingContext);
        vertx.getOrCreateContext().config()
                .put("accessible",
                        new JsonObject()
                                .put("endpoints", new JsonObject(Map.of("GET-/allowedPath", true)))
                                .put("endpointsRegex", new JsonObject(Map.of("GET-/allowedPath/.*/user", true,
                                        "GET-/allowedPathWithNumber/(\\d)/user", true))));
        accessibleEndpointHandler = new AccessibleEndpointHandler(vertx);
    }

    @Test
    void shouldNotAllowEndpointToBeServicedIfItIsNotFoundInAccessibleEndpointList() {
        when(routingContext.request().path()).thenReturn("/disallowedPath");
        when(routingContext.request().method()).thenReturn(HttpMethod.GET);

        accessibleEndpointHandler.handle(routingContext);

        verify(routingContext.response()).setStatusCode(404);
    }

    @Test
    void shouldAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointList() {
        when(routingContext.request().path()).thenReturn("/allowedPath");
        when(routingContext.request().method()).thenReturn(HttpMethod.GET);

        accessibleEndpointHandler.handle(routingContext);

        verify(routingContext, times(0)).response();
        verify(routingContext).next();
    }

    @Test
    void shouldAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointRegexList() {
        when(routingContext.request().path()).thenReturn("/allowedPath/id/user");
        when(routingContext.request().method()).thenReturn(HttpMethod.GET);

        accessibleEndpointHandler.handle(routingContext);

        verify(routingContext, times(0)).response();
        verify(routingContext).next();
    }

    @Test
    void shouldNotAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointRegexList() {
        when(routingContext.request().path()).thenReturn("/allowedPathWithNumber/id/user");
        when(routingContext.request().method()).thenReturn(HttpMethod.GET);

        accessibleEndpointHandler.handle(routingContext);

        verify(routingContext.response()).setStatusCode(404);
    }
}
