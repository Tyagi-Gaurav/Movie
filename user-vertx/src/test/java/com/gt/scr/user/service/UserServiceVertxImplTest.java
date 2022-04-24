package com.gt.scr.user.service;

import com.gt.scr.domain.User;
import com.gt.scr.user.util.UserBuilder;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.eventbus.ReplyFailure;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(VertxExtension.class)
class UserServiceVertxImplTest {
    private static final String ACCOUNT_CREATE_ADDRESS = "account.create.repo";
    private static final String USER_FETCH_BY_USERNAME_ADDRESS = "user.fetch.by.userName";
    private static final String USER_FETCH_BY_ID_ADDRESS = "user.fetch.by.userId";
    private static final String USER_DELETE_BY_ID_ADDRESS = "user.delete.by.userId";
    private static final String USER_FETCH_ALL = "user.fetch.all";
    private UserServiceV2 userServiceV2;

    @BeforeEach
    void setUp(Vertx vertx) {
        vertx.getOrCreateContext().config().put("bus.account.create.repo.timeoutInMs", 100);
        vertx.getOrCreateContext().config().put("bus.user.fetch.by.userName.timeoutInMs", 100);
        vertx.getOrCreateContext().config().put("bus.user.fetch.by.userId.timeoutInMs", 100);
        vertx.getOrCreateContext().config().put("bus.user.delete.by.userId.timeoutInMs", 100);
        vertx.getOrCreateContext().config().put("bus.user.fetch.all.timeoutInMs", 100);
        userServiceV2 = new UserServiceVertxImpl(vertx);
    }

    @DisplayName("Should return Success when user is Inserted into database")
    @Test
    void shouldReturnSuccessForInsert(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        AtomicInteger callCounter = new AtomicInteger();
        User user = userBuilder.build();

        vertx.eventBus().consumer(ACCOUNT_CREATE_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(JsonObject.mapFrom(user));
            handler.reply(new JsonObject());
            callCounter.incrementAndGet();
        });

        userServiceV2.add(user)
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.succeeded()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should handle failure when timeout occurs on the bus")
    @Test
    void shouldHandleFailureOnTimeoutForAddUser(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        User user = userBuilder.build();

        vertx.eventBus().consumer(ACCOUNT_CREATE_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(JsonObject.mapFrom(user));
            await().atLeast(Duration.ofMillis(200)).until(() -> true);
        });

        userServiceV2.add(user)
                .onComplete(ar -> {
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    vertxTestContext.verify(() -> assertThat(replyException.failureType())
                            .isEqualTo(ReplyFailure.TIMEOUT));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should return failure when Insert into Database fails")
    @Test
    void shouldReturnFailureWhenDaoReturnsFailure(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        AtomicInteger callCounter = new AtomicInteger();
        User user = userBuilder.build();

        vertx.eventBus().consumer(ACCOUNT_CREATE_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(JsonObject.mapFrom(user));
            callCounter.incrementAndGet();
            handler.fail(2, "Database Exception");
        });

        userServiceV2.add(user)
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.failed()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should fetch user by name")
    @Test
    void shouldFetchUserByName(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        AtomicInteger callCounter = new AtomicInteger();
        User user = userBuilder.build();

        vertx.eventBus().consumer(USER_FETCH_BY_USERNAME_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userName", user.username()));
            handler.reply(JsonObject.mapFrom(user));
            callCounter.incrementAndGet();
        });

        userServiceV2.findByUsername(user.username())
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.succeeded()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    vertxTestContext.verify(() -> assertThat(ar.result()).isEqualTo(JsonObject.mapFrom(user)));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should handle timeout when fetch user by name")
    @Test
    void shouldHandleFailureOnTimeoutForFetchUserByName(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        User user = userBuilder.build();

        vertx.eventBus().consumer(USER_FETCH_BY_USERNAME_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userName", user.username()));
            await().atLeast(Duration.ofMillis(200)).until(() -> true);
        });

        userServiceV2.findByUsername(user.username())
                .onComplete(ar -> {
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    vertxTestContext.verify(() -> assertThat(replyException.failureType())
                            .isEqualTo(ReplyFailure.TIMEOUT));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should fetch user by id")
    @Test
    void shouldFetchUserById(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        AtomicInteger callCounter = new AtomicInteger();
        User user = userBuilder.build();

        vertx.eventBus().consumer(USER_FETCH_BY_ID_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userId", user.id().toString()));
            handler.reply(JsonObject.mapFrom(user));
            callCounter.incrementAndGet();
        });

        userServiceV2.findByUserId(user.id())
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.succeeded()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    vertxTestContext.verify(() -> assertThat(ar.result()).isEqualTo(JsonObject.mapFrom(user)));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should handle timeout when fetch user by id")
    @Test
    void shouldHandleFailureOnTimeoutForFetchUserById(Vertx vertx, VertxTestContext vertxTestContext) {
        UserBuilder userBuilder = UserBuilder.aUser();
        User user = userBuilder.build();

        vertx.eventBus().consumer(USER_FETCH_BY_ID_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userName", user.username()));
            await().atLeast(Duration.ofMillis(200)).until(() -> true);
        });

        userServiceV2.findByUserId(user.id())
                .onComplete(ar -> {
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    vertxTestContext.verify(() -> assertThat(replyException.failureType())
                            .isEqualTo(ReplyFailure.TIMEOUT));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should delete user")
    @Test
    void shouldDeleteUserById(Vertx vertx, VertxTestContext vertxTestContext) {
        AtomicInteger callCounter = new AtomicInteger();
        UUID userId = UUID.randomUUID();

        vertx.eventBus().consumer(USER_DELETE_BY_ID_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userId", userId.toString()));
            handler.reply(new JsonObject().put("userId", userId.toString()));
            callCounter.incrementAndGet();
        });

        userServiceV2.delete(userId)
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.succeeded()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    JsonObject result = ar.result();
                    vertxTestContext.verify(() -> assertThat(result.getString("userId")).isEqualTo(userId.toString()));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should handle timeout when delete user by id")
    @Test
    void shouldHandleFailureOnTimeoutForDeleteUserById(Vertx vertx, VertxTestContext vertxTestContext) {
        UUID userId = UUID.randomUUID();

        vertx.eventBus().consumer(USER_DELETE_BY_ID_ADDRESS, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userId", userId.toString()));
            await().atLeast(Duration.ofMillis(200)).until(() -> true);
        });

        userServiceV2.delete(userId)
                .onComplete(ar -> {
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    vertxTestContext.verify(() -> assertThat(replyException.failureType())
                            .isEqualTo(ReplyFailure.TIMEOUT));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should get all users")
    @Test
    void shouldFetchAllUsers(Vertx vertx, VertxTestContext vertxTestContext) {
        AtomicInteger callCounter = new AtomicInteger();
        List<User> userList = List.of(UserBuilder.aUser().build(), UserBuilder.aUser().build());

        vertx.eventBus().consumer(USER_FETCH_ALL, handler -> {
            List<JsonObject> jsonObjects = userList.stream().map(JsonObject::mapFrom).collect(Collectors.toList());

            handler.reply(new JsonObject().put("userList", new JsonArray(jsonObjects)));
            callCounter.incrementAndGet();
        });

        userServiceV2.fetchAll()
                .onComplete(ar -> {
                    vertxTestContext.verify(() -> assertThat(ar.succeeded()).isTrue());
                    vertxTestContext.verify(() -> assertThat(callCounter.get()).isEqualTo(1));
                    JsonObject result = ar.result();
                    List<JsonObject> jsonUserList = result.getJsonArray("userList").getList();
                    List<User> users = jsonUserList.stream().map(jo -> jo.mapTo(User.class)).toList();
                    vertxTestContext.verify(() -> assertThat(users).containsAll(userList));
                    vertxTestContext.completeNow();
                });
    }

    @DisplayName("Should handle timeout when fetch all users")
    @Test
    void shouldHandleTimeoutWhenFetchAllUsers(Vertx vertx, VertxTestContext vertxTestContext) {
        UUID userId = UUID.randomUUID();

        vertx.eventBus().consumer(USER_FETCH_ALL, handler -> {
            assertThat(handler.body()).isEqualTo(new JsonObject().put("userId", userId.toString()));
            await().atLeast(Duration.ofMillis(200)).until(() -> true);
        });

        userServiceV2.fetchAll()
                .onComplete(ar -> {
                    Throwable cause = ar.cause();
                    vertxTestContext.verify(() -> assertThat(cause).isInstanceOf(ReplyException.class));
                    ReplyException replyException = (ReplyException) cause;
                    vertxTestContext.verify(() -> assertThat(replyException.failureType())
                            .isEqualTo(ReplyFailure.TIMEOUT));
                    vertxTestContext.completeNow();
                });
    }
}