package com.gt.scr.movie.system;

import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.ext.user.StatusResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAppHealthCheckTest {

    @Mock
    private UpstreamClient<Void, StatusResponseDTO> userStatusClient;

    private UserAppHealthCheck userAppHealthCheck;

    @BeforeEach
    void setUp() {
        userAppHealthCheck = new UserAppHealthCheck(userStatusClient);
    }

    @Test
    void shouldReturnSuccessWhenHealthIsOk() {
        //given
        when(userStatusClient.execute(null)).thenReturn(Mono.just(new StatusResponseDTO("UP")));

        //when
        Health health = userAppHealthCheck.health();

        //then
        assertThat(health).isEqualTo(Health.up().build());
    }

    @Test
    void shouldReturnFailureWhenHealthIsNotOK() {
        //given
        when(userStatusClient.execute(null)).thenReturn(Mono.just(new StatusResponseDTO("DOWN")));

        //when
        Health health = userAppHealthCheck.health();

        //then
        assertThat(health).isEqualTo(Health.down(new RuntimeException("health check failed")).build());
    }

    @Test
    void shouldReturnFailureWhenClientReturnsError() {
        //given
        when(userStatusClient.execute(null)).thenReturn(Mono.error(new RuntimeException("error")));

        //when
        Health health = userAppHealthCheck.health();

        //then
        assertThat(health).isEqualTo(Health.down(new RuntimeException("health check failed")).build());
    }
}