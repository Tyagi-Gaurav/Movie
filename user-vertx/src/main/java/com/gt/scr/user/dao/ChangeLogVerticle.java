package com.gt.scr.user.dao;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgConnectOptions;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.DriverManager;

public class ChangeLogVerticle extends AbstractVerticle {

    private final PgConnectOptions pgConnectOptions;

    public ChangeLogVerticle(PgConnectOptions pgConnectOptions) {
        this.pgConnectOptions = pgConnectOptions;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        String jdbcUrl= String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s&loggerLevel=OFF",
                pgConnectOptions.getHost(),
                pgConnectOptions.getPort(),
                pgConnectOptions.getDatabase(),
                pgConnectOptions.getUser(),
                pgConnectOptions.getPassword());

        Database correctDatabaseImplementation = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                new JdbcConnection(DriverManager.getConnection(jdbcUrl)));
        try(Liquibase liquibase = new Liquibase("db.changelog/userchangelog.sql", new ClassLoaderResourceAccessor(), correctDatabaseImplementation)) {
            liquibase.update(new Contexts());
        }
        startPromise.complete();
    }
}
