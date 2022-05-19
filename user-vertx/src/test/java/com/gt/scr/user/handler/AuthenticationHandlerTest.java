package com.gt.scr.user.handler;

import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.service.UserServiceV2;
import com.gt.scr.user.util.TestUtils;
import com.gt.scr.utils.DataEncoder;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationHandlerTest {
    @Mock
    private DataEncoder dataEncoder;

    @Mock
    private UserServiceV2 userService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RoutingContext routingContext;

    @InjectMocks
    private AuthenticationHandler authenticationHandler;

    @Test
    void shouldHandleEncodingError() throws IOException {
        LoginRequestDTO loginRequestDTO = TestUtils.testLoginRequest();
        when(routingContext.body().asPojo(LoginRequestDTO.class))
                .thenReturn(loginRequestDTO);
        when(userService.findByUsername(loginRequestDTO.userName()))
                .thenReturn(Future.succeededFuture(JsonObject.mapFrom(TestUtils.testUser())));

        when(dataEncoder.encode(anyString())).thenThrow(new IOException("Error Occurred"));

        authenticationHandler.handle(routingContext);

        verify(routingContext.response()).setStatusCode(500);
    }
}