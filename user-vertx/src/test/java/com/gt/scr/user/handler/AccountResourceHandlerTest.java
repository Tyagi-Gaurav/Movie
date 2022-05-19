package com.gt.scr.user.handler;

import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.util.TestUtils;
import com.gt.scr.utils.DataEncoder;
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
class AccountResourceHandlerTest {

    @Mock
    private DataEncoder dataEncoder;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RoutingContext routingContext;

    @InjectMocks
    private AccountResourceHandler accountResourceHandler;

    @Test
    void shouldHandleEncodingError() throws IOException {
        AccountCreateRequestDTO accountCreateRequestDTO = TestUtils.testAccountCreateRequestDTO();
        when(routingContext.body().asPojo(AccountCreateRequestDTO.class))
                .thenReturn(accountCreateRequestDTO);

        when(dataEncoder.encode(anyString())).thenThrow(new IOException("Error Occurred"));

        accountResourceHandler.handle(routingContext);

        verify(routingContext.response()).setStatusCode(500);
    }
}