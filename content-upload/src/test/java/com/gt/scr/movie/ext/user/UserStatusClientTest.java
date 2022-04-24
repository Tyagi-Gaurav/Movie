package com.gt.scr.movie.ext.user;

import com.gt.scr.resilience.Resilience;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserStatusClientTest {
    @Test
    void shouldHaveResilienceAnnotationForExternalCalls() throws NoSuchMethodException {
        Resilience annotation = UserStatusClient.class.getMethod("execute", Void.class)
                .getAnnotation(Resilience.class);

        assertThat(annotation).isNotNull();
        assertThat(annotation.value()).isEqualTo("user");
    }
}