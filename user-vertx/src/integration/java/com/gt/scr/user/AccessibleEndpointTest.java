package com.gt.scr.user;

import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.junit5.EmbeddedPostgresExtension;
import com.opentable.db.postgres.junit5.SingleInstancePostgresExtension;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.TimeUnit;

@ExtendWith(VertxExtension.class)
public class AccessibleEndpointTest {
    @RegisterExtension
    public final static SingleInstancePostgresExtension singleInstancePostgresExtension = EmbeddedPostgresExtension.singleInstance();

    private ScenarioExecutor scenarioExecutor;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext vertxTestContext) {
        EmbeddedPostgres embeddedPostgres = singleInstancePostgresExtension.getEmbeddedPostgres();
        JsonObject config = new JsonObject()
                .put("db.port", embeddedPostgres.getPort())
                .put("db.name", "postgres")
                .put("db.host", "localhost")
                .put("db.user", embeddedPostgres.getUserName())
                .put("db.password", embeddedPostgres.getPassword())
                .put("auth.token.key", "19CA249C582715657BDCAB1FB31E69F854443A4FE3CBAFFD215E3F3676")
                .put("http.port", 5050)
                .put("toggle", new JsonObject()
                        .put("endpoints", new JsonArray().add("POST-/api/user/account/create")));

        String baseUrl = "http://localhost:" + 5050 + "/api/";
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, embeddedPostgres.getPostgresDatabase());

        vertx.deployVerticle(new Application(), new DeploymentOptions()
                .setConfig(config), vertxTestContext.succeeding(event -> {
            scenarioExecutor.addAdminUser(embeddedPostgres.getPostgresDatabase());
            vertxTestContext.completeNow();
        }));
    }

    @Test
    @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
    void shouldNotBeAbleToCreateUserWhenEndpointIsSwitchedOff00() {
        AccountCreateRequestDTO accountCreateRequestDTO = AccountCreateRequestBuilder.accountCreateRequest().build();

        scenarioExecutor
                .userIsCreatedFor(accountCreateRequestDTO)
                .then().expectReturnCode(404);
    }
}
