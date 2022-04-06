package com.gt.scr.user.handler;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(VertxExtension.class)
class ConfigPublishHandlerTest {
    private final RoutingContext routingContext = Mockito.mock(RoutingContext.class, Mockito.RETURNS_DEEP_STUBS);

    private final JsonObject accessibleEndpointsConfig = new JsonObject()
            .put("endpoints", new JsonObject(Map.of("GET-/allowedPath", true)))
            .put("endpointsRegex", new JsonObject(Map.of("GET-/allowedPath/.*/user", true,
                    "GET-/allowedPathWithNumber/(\\d)/user", true)));

    private final JsonObject config = new JsonObject()
            .put("accessible", accessibleEndpointsConfig)
            .put("db.host", "local.postgres")
            .put("db.password", "abcd");

    private final ConfigPublishHandler configPublishHandler = new ConfigPublishHandler(config);

    @BeforeEach
    void setUp(Vertx vertx) {
        MockitoAnnotations.openMocks(routingContext);
        when(routingContext.vertx()).thenReturn(vertx);
    }

    @Test
    void shouldPublishConfig() {
        ArgumentCaptor<Buffer> bufferArgumentCaptor = ArgumentCaptor.forClass(Buffer.class);
        when(routingContext.response().write(bufferArgumentCaptor.capture())).thenReturn(Future.succeededFuture());

        configPublishHandler.handle(routingContext);

        Buffer value = bufferArgumentCaptor.getValue();
        JsonObject actualResponse = new JsonObject(value);
        assertThat(actualResponse.getValue("accessible")).isEqualTo(accessibleEndpointsConfig);
        verify(routingContext.response()).setStatusCode(200);
    }

    @Test
    void shouldHideSensitiveFields() {
        ArgumentCaptor<Buffer> bufferArgumentCaptor = ArgumentCaptor.forClass(Buffer.class);
        when(routingContext.response().write(bufferArgumentCaptor.capture())).thenReturn(Future.succeededFuture());

        configPublishHandler.handle(routingContext);

        Buffer value = bufferArgumentCaptor.getValue();
        JsonObject actualResponse = new JsonObject(value);
        assertThat(actualResponse.getValue("db.password")).isEqualTo("******");
        verify(routingContext.response()).setStatusCode(200);
    }
}