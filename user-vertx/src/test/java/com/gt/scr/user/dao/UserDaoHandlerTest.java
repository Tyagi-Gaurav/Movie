package com.gt.scr.user.dao;

import com.gt.scr.domain.User;
import com.gt.scr.user.util.UserBuilder;
import com.opentable.db.postgres.embedded.ConnectionInfo;
import com.opentable.db.postgres.embedded.DatabasePreparer;
import com.opentable.db.postgres.embedded.LiquibasePreparer;
import com.opentable.db.postgres.junit5.EmbeddedPostgresExtension;
import com.opentable.db.postgres.junit5.PreparedDbExtension;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({VertxExtension.class})
class UserDaoHandlerTest {
    private static final DatabasePreparer databasePreparer = LiquibasePreparer.forClasspathLocation("db.changelog/userchangelog.sql");
    private static final Pattern pattern = Pattern.compile(".*:(\\d.+)\\/(.+)\\?.*$");

    @RegisterExtension
    public static PreparedDbExtension preparedDbExtension = EmbeddedPostgresExtension.preparedDatabase(databasePreparer);

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext vertxTestContext) {
        ConnectionInfo connectionInfo = preparedDbExtension.getConnectionInfo();
        String url = connectionInfo.getUrl();
        Matcher matcher = pattern.matcher(url);
        assertThat(matcher.matches()).isTrue();
        PgConnectOptions pgConnectOptions = new PgConnectOptions()
                .setPort(Integer.parseInt(matcher.group(1)))
                .setHost("localhost")
                .setDatabase(matcher.group(2))
                .setReconnectAttempts(2)
                .setReconnectInterval(1000)
                .setUser(connectionInfo.getUser())
                .setPassword(connectionInfo.getPassword());

        PgPool pool = PgPool.pool(vertx, pgConnectOptions, new PoolOptions());

        vertx.deployVerticle(new UserDaoHandler(pool),
                vertxTestContext.succeeding(event -> {
                    vertxTestContext.completeNow(); //Notify that setup has completed.
                }));
    }

    @Test
    void shouldCreateAccount(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser()
                .withRoles(Collections.emptyList())
                .build();
        vertx.eventBus().request("account.create.repo",
                JsonObject.mapFrom(expectedUser), ar -> {
                    ctx.verify(() -> assertTrue(ar.succeeded(), () -> ar.cause().getMessage()));
                    ctx.verify(() -> assertThat(ar.result().body()).isEqualTo(new JsonObject()));
                    User user = fetchUser(expectedUser.id().toString());
                    ctx.verify(() -> assertThat(user).isNotNull());
                    ctx.verify(() -> assertThat(user.firstName()).isEqualTo(expectedUser.firstName()));
                    ctx.verify(() -> assertThat(user.id()).isEqualTo(expectedUser.id()));
                    ctx.verify(() -> assertThat(user.password()).isEqualTo(expectedUser.password()));
                    ctx.verify(() -> assertThat(user.getRole()).isEqualTo(""));
                    ctx.completeNow();
                });
    }

    @Test
    void shouldHandleCreateAccountFailure(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser()
                .withUserName(RandomStringUtils.randomAlphabetic(50))
                .build();
        vertx.eventBus().request("account.create.repo",
                JsonObject.mapFrom(expectedUser), ar -> {
                    ctx.verify(() -> assertTrue(ar.failed()));
                    Throwable cause = ar.cause();
                    ctx.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    int DATABASE_FAILURE = 1;
                    ctx.verify(() -> assertThat(replyException.failureCode()).isEqualTo(DATABASE_FAILURE));
                    ctx.completeNow();
                });
    }

    @Test
    void shouldFetchByUserName(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser().build();
        addUser(expectedUser);

        vertx.eventBus().request("user.fetch.by.userName",
                new JsonObject().put("userName", expectedUser.username()), ar -> {
                    ctx.verify(() -> assertTrue(ar.succeeded(), () -> ar.cause().getMessage()));
                    User actualUser = ((JsonObject) ar.result().body()).mapTo(User.class);
                    ctx.verify(() -> assertThat(actualUser).isEqualTo(expectedUser));
                    ctx.completeNow();
                });
    }

    @Test
    void shouldFetchByUserId(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser().build();
        addUser(expectedUser);

        vertx.eventBus().request("user.fetch.by.userId",
                new JsonObject().put("userId", expectedUser.id().toString()), ar -> {
                    ctx.verify(() -> assertTrue(ar.succeeded(), () -> ar.cause().getMessage()));
                    User actualUser = ((JsonObject) ar.result().body()).mapTo(User.class);
                    ctx.verify(() -> assertThat(actualUser).isEqualTo(expectedUser));
                    ctx.completeNow();
                });
    }

    @Test
    void shouldFetchAllUsers(Vertx vertx, VertxTestContext ctx) {
        User expectedUser1 = UserBuilder.aUser().build();
        User expectedUser2 = UserBuilder.aUser().build();
        addUser(expectedUser1, expectedUser2);

        vertx.eventBus().request("user.fetch.all",
                null, ar -> {
                    ctx.verify(() -> assertTrue(ar.succeeded(), () -> ar.cause().getMessage()));
                    List<JsonObject> allUsers = ((JsonObject) ar.result().body()).getJsonArray("userList").getList();
                    List<User> actualUsersList = allUsers.stream().map(jo -> jo.mapTo(User.class)).collect(Collectors.toList());
                    ctx.verify(() -> assertThat(actualUsersList).containsAll(List.of(expectedUser1, expectedUser2)));
                    ctx.completeNow();
                });
    }

    @Test
    void whenNoUserFoundFetchByUserIdShouldReturnFailure(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser().build();

        vertx.eventBus().request("user.fetch.by.userId",
                new JsonObject().put("userId", expectedUser.id().toString()), ar -> {
                    ctx.verify(() -> assertTrue(ar.failed()));
                    Throwable cause = ar.cause();
                    ctx.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    int NO_RECORD_FOUND = 2;
                    ctx.verify(() -> assertThat(replyException.failureCode()).isEqualTo(NO_RECORD_FOUND));
                    ctx.completeNow();
                });
    }

    @Test
    void whenNoUserFoundFetchByUserNameShouldReturnFailure(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser().build();

        vertx.eventBus().request("user.fetch.by.userName",
                new JsonObject().put("userName", expectedUser.username()), ar -> {
                    ctx.verify(() -> assertTrue(ar.failed()));
                    Throwable cause = ar.cause();
                    ctx.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    int NO_RECORD_FOUND = 2;
                    ctx.verify(() -> assertThat(replyException.failureCode()).isEqualTo(NO_RECORD_FOUND));
                    ctx.completeNow();
                });
    }

    @Test
    void shouldDeleteUser(Vertx vertx, VertxTestContext ctx) {
        User expectedUser = UserBuilder.aUser().build();
        addUser(expectedUser);

        vertx.eventBus().request("user.delete.by.userId",
                new JsonObject().put("userId", expectedUser.id().toString()), ar -> {
                    ctx.verify(() -> assertTrue(ar.succeeded(), () -> ar.cause().getMessage()));
                    User user = fetchUser(expectedUser.id().toString());
                    ctx.verify(() -> assertThat(user).isNull());
                    ctx.completeNow();
                });
    }

    private void addUser(User... users) {
        Arrays.stream(users)
                .forEach(user -> {
                    try (Connection connection = preparedDbExtension.getTestDatabase().getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(
                                 "INSERT INTO USER_SCHEMA.USER_TABLE (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) VALUES (?, ?, ?, ?, ?, ?)")) {

                        preparedStatement.setString(1, user.id().toString());
                        preparedStatement.setString(2, user.username());
                        preparedStatement.setString(3, user.firstName());
                        preparedStatement.setString(4, user.lastName());
                        preparedStatement.setString(5, user.password());
                        preparedStatement.setString(6, StringUtils.join(user.authorities(), ","));

                        preparedStatement.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

    }

    private User fetchUser(String id) {
        try (Connection connection = preparedDbExtension.getTestDatabase().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * From USER_SCHEMA.USER_TABLE WHERE ID = ?")) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(UUID.fromString(resultSet.getString("ID")),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("USER_NAME"),
                        resultSet.getString("PASSWORD"),
                        Collections.emptyList());
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}