package com.gt.scr.user;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) {
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setOptional(true)
                .setConfig(new JsonObject().put("path", "conf/config.json"));
        ConfigStoreOptions sysPropsStore = new ConfigStoreOptions().setType("sys");
        ConfigStoreOptions envPropsStore = new ConfigStoreOptions().setType("env");
        ConfigRetrieverOptions options = new ConfigRetrieverOptions()
                .addStore(fileStore)
                .addStore(sysPropsStore)
                .addStore(envPropsStore);
        ConfigRetriever configRetriever = ConfigRetriever.create(vertx, options);

        configRetriever.getConfig(ar -> {
            if (ar.failed()) {
                LOG.error("Failed to start application. No Config found.");
            } else {
                vertx.deployVerticle(Application.class,
                        new DeploymentOptions().setConfig(ar.result()));
            }
        });
    }
}
