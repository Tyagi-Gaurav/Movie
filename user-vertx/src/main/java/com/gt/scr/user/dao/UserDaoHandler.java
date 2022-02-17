package com.gt.scr.user.dao;

import com.gt.scr.domain.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gt.scr.user.config.EventBusAddress.*;
import static com.gt.scr.user.exception.FailureCodes.DATABASE_FAILURE;
import static com.gt.scr.user.exception.FailureCodes.NO_RECORD_FOUND;

public class UserDaoHandler extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(UserDaoHandler.class);
    private final PgPool pool;

    private static final String ID = "ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ROLES = "ROLES";
    private static final String SCHEMA = "USER_SCHEMA";

    private static final String INSERT_USERS = String.format("INSERT INTO %s.USER_TABLE (%s, %s, %s, %s, %s, %s) VALUES ($1, $2, $3, $4, $5, $6)"
            , SCHEMA, ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES);

    private static final String FETCH_BY_USER_NAME = String.format("SELECT * FROM %s.USER_TABLE WHERE %s = $1", SCHEMA, USER_NAME);

    private static final String FETCH_BY_USER_ID = String.format("SELECT * FROM %s.USER_TABLE WHERE %s = $1", SCHEMA, ID);

    private static final String FETCH_ALL_USERS = String.format("SELECT * FROM %s.USER_TABLE", SCHEMA);

    private static final String DELETE_BY_USER_ID = String.format("DELETE FROM %s.USER_TABLE WHERE %s = $1", SCHEMA, ID);

    public UserDaoHandler(PgPool pool) {
        this.pool = pool;
    }

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(REPO_ACCOUNT_CREATE.getAddress(), this::create);
        eventBus.consumer(REPO_USER_FETCH_BY_USER_NAME.getAddress(), this::fetchByUserName);
        eventBus.consumer(REPO_USER_FETCH_BY_USER_ID.getAddress(), this::fetchByUserId);
        eventBus.consumer(REPO_USER_DELETE_BY_USER_ID.getAddress(), this::deleteByUserId);
        eventBus.consumer(REPO_USER_FETCH_ALL.getAddress(), this::fetchAllUsers);
    }

    private void fetchAllUsers(Message<JsonObject> tMessage) {
        pool.preparedQuery(FETCH_ALL_USERS)
                .execute(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> rowSet = ar.result();
                        if (rowSet.rowCount() > 0) {
                            List<User> users = new ArrayList<>();
                            rowSet.iterator().forEachRemaining(
                                    row -> users.add(new User(UUID.fromString(row.getString(ID.toLowerCase())),
                                            row.getString(FIRST_NAME.toLowerCase()),
                                            row.getString(LAST_NAME.toLowerCase()),
                                            row.getString(USER_NAME.toLowerCase()),
                                            row.getString(PASSWORD.toLowerCase()),
                                            Stream.of(row.getString(ROLES.toLowerCase()).split(","))
                                                    .toList()))
                            );

                            List<JsonObject> jsonObjectList = users.stream().map(JsonObject::mapFrom).toList();
                            tMessage.reply(new JsonObject().put("userList", new JsonArray(jsonObjectList)));
                            return;
                        }
                    }

                    tMessage.fail(NO_RECORD_FOUND.getFailureCode(), "No users found");
                });
    }

    private void deleteByUserId(Message<JsonObject> tMessage) {
        JsonObject jsonObject = tMessage.body();
        String userId = jsonObject.getString("userId");

        pool.preparedQuery(DELETE_BY_USER_ID)
                .execute(Tuple.of(userId), ar -> {
                    if (ar.succeeded()) {
                        tMessage.reply(new JsonObject());
                    } else {
                        Throwable cause = ar.cause();
                        LOG.error(cause.getMessage(), cause);
                        tMessage.fail(DATABASE_FAILURE.getFailureCode(), cause.getMessage());
                    }
                });
    }

    private void fetchByUserName(Message<JsonObject> tMessage) {
        JsonObject jsonObject = tMessage.body();
        String userName = jsonObject.getString("userName");

        pool.preparedQuery(FETCH_BY_USER_NAME)
                .execute(Tuple.of(userName), ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> rowSet = ar.result();
                        if (rowSet.rowCount() > 0) {
                            Row row = rowSet.iterator().next();
                            tMessage.reply(JsonObject.mapFrom(
                                    new User(UUID.fromString(row.getString(ID.toLowerCase())),
                                            row.getString(FIRST_NAME.toLowerCase()),
                                            row.getString(LAST_NAME.toLowerCase()),
                                            row.getString(USER_NAME.toLowerCase()),
                                            row.getString(PASSWORD.toLowerCase()),
                                            Stream.of(row.getString(ROLES.toLowerCase()).split(","))
                                                    .toList())));
                            return;
                        }
                    }

                    tMessage.fail(NO_RECORD_FOUND.getFailureCode(), "No user found");
                });
    }

    private void fetchByUserId(Message<JsonObject> tMessage) {
        JsonObject jsonObject = tMessage.body();
        String userId = jsonObject.getString("userId");

        pool.preparedQuery(FETCH_BY_USER_ID)
                .execute(Tuple.of(userId), ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> rowSet = ar.result();
                        if (rowSet.rowCount() > 0) {
                            Row row = rowSet.iterator().next();
                            tMessage.reply(JsonObject.mapFrom(
                                    new User(UUID.fromString(row.getString(ID.toLowerCase())),
                                            row.getString(FIRST_NAME.toLowerCase()),
                                            row.getString(LAST_NAME.toLowerCase()),
                                            row.getString(USER_NAME.toLowerCase()),
                                            row.getString(PASSWORD.toLowerCase()),
                                            Stream.of(row.getString(ROLES.toLowerCase()).split(","))
                                                    .toList())));
                            return;
                        }
                    }

                    tMessage.fail(NO_RECORD_FOUND.getFailureCode(), "No user found");
                });
    }

    private void create(Message<JsonObject> userMessage) {
        User user = userMessage.body().mapTo(User.class);

        pool.preparedQuery(INSERT_USERS)
                .execute(Tuple.of(
                        user.id().toString(),
                        user.username(),
                        user.firstName(),
                        user.lastName(),
                        user.password(),
                        StringUtils.join(user.authorities(), ",")
                ), ar -> {
                    if (ar.succeeded()) {
                        userMessage.reply(new JsonObject());
                    } else {
                        Throwable cause = ar.cause();
                        LOG.error(cause.getMessage(), cause);
                        userMessage.fail(DATABASE_FAILURE.getFailureCode(), cause.getMessage());
                    }
                });
    }
}
