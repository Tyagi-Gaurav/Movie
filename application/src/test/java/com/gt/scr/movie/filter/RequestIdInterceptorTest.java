package com.gt.scr.movie.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestIdInterceptorTest {

    private RequestIdInterceptor requestIdInterceptor = new RequestIdInterceptor();

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Object handler;

    @Test
    void shouldGenerateRequestIdWhenNoIdPresentInRequest() {
        //when
        requestIdInterceptor.preHandle(httpServletRequest,
                httpServletResponse, handler);

        //then
        ArgumentCaptor<String> uuidArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(httpServletResponse).setHeader(eq("requestId"), uuidArgumentCaptor.capture());

        String value = uuidArgumentCaptor.getValue();
        assertThat(UUID.fromString(value).toString()).isEqualTo(value);
    }

    @Test
    void shouldUseRequestIdWhenIdPassedInRequest() {
        //given
        String requestId = "requestIdPassedByClient";
        when(httpServletRequest.getHeader("requestId")).thenReturn(requestId);


        //when
        requestIdInterceptor.preHandle(httpServletRequest,
                httpServletResponse, handler);

        //then
        verify(httpServletResponse).setHeader("requestId", requestId);
    }
}