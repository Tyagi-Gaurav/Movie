package com.gt.scr.spc.resilience;

import com.gt.scr.resilience.CircuitBreakerConfig;
import com.gt.scr.resilience.Resilience;
import com.gt.scr.resilience.ThreadPoolBulkheadConfig;
import com.gt.scr.resilience.TimeLimitConfig;
import com.gt.scr.spc.config.ResilienceConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ResilienceComponentProcessorTest {

    private final MeterRegistry meterRegistry = new SimpleMeterRegistry();
    private final ResilienceComponentProcessor resilienceComponentProcessor = new ResilienceComponentProcessor(new ResilienceConfig(
            Map.of("testUpStream", new CircuitBreakerConfig(true, 100, 10, 10, 1)),
            Map.of("testUpStream", new ThreadPoolBulkheadConfig(true, 2, 2, 5000, 5000, 2)),
            Map.of("testUpStream", new TimeLimitConfig(true, 50))
    ), meterRegistry, Executors.newScheduledThreadPool(3));

    private Object testProxy;

    @BeforeEach
    void setUp() {
        TestBean testBean = new TestBean();
        resilienceComponentProcessor.postProcessBeforeInitialization(testBean, "testBean");
        testProxy = resilienceComponentProcessor.postProcessAfterInitialization(testBean, "testBean");
    }

    @Test
    void shouldUseCircuitBreakerWhenConfigured() throws Throwable {
        String input = "testString-" + UUID.randomUUID();
        Method doSomethingWithResilience = TestBean.class.getMethod("doSomethingWithResilience", String.class);
        Proxy.getInvocationHandler(testProxy).invoke(testProxy, doSomethingWithResilience, new Object[]{input});

        assertThat(meterRegistry.timer("resilience4j.circuitbreaker.calls", "kind", "successful",
                "name", "testBean.doSomethingWithResilience.circuitBreaker").count()).isEqualTo(1);
    }

    @Test
    void shouldCircuitBreakWhenCallsFail() throws Throwable {
        String input = "testString-" + UUID.randomUUID();
        int delay = 40;
        Method doSomethingWithResilienceWithDelay = TestBean.class.getMethod("doSomethingWithResilienceWithDelay", String.class, Integer.class);
        Proxy.getInvocationHandler(testProxy).invoke(testProxy, doSomethingWithResilienceWithDelay, new Object[]{input, delay});

        assertThatExceptionOfType(ExecutionException.class).isThrownBy(() ->
                Proxy.getInvocationHandler(testProxy).invoke(testProxy, doSomethingWithResilienceWithDelay, new Object[]{input, delay}));

        String cbTag = "testBean.doSomethingWithResilienceWithDelay.circuitBreaker";
        assertThat(meterRegistry.get("resilience4j.circuitbreaker.slow.calls")
                .tags("name", cbTag, "kind", "successful")
                .gauge().value()).isEqualTo(1);

        assertThat(meterRegistry.timer("resilience4j.circuitbreaker.calls", "kind", "successful",
                "name", cbTag).count()).isEqualTo(1);

        assertThat(meterRegistry.get("resilience4j.circuitbreaker.state").tags("name", cbTag, "state", "open")
                .gauge().value()).isEqualTo(1);
    }

    @Test
    void shouldNotUseResilienceWhenBeanMethodDoesNotHaveAnnotation() throws Throwable {
        String input = "testString-" + UUID.randomUUID();
        Method doSomethingWithResilience = TestBean.class.getMethod("doSomethingWithoutResilience", String.class);
        Proxy.getInvocationHandler(testProxy).invoke(testProxy, doSomethingWithResilience, new Object[]{input});

        assertThat(meterRegistry.timer("resilience4j.circuitbreaker.calls", "kind", "successful",
                "name", "testBean.doSomethingWithoutResilience.circuitBreaker").count()).isEqualTo(0);

        assertThat(meterRegistry.timer("resilience4j.circuitbreaker.calls", "kind", "failed",
                "name", "testBean.doSomethingWithoutResilience.circuitBreaker").count()).isEqualTo(0);

        assertThat(meterRegistry.timer("resilience4j.circuitbreaker.calls", "kind", "ignored",
                "name", "testBean.doSomethingWithoutResilience.circuitBreaker").count()).isEqualTo(0);
    }


    @Test
    void shouldTimeoutWhenMethodTakesLongTime() throws Throwable {
        String input = "testString-" + UUID.randomUUID();
        int delay = 70;
        Method doSomethingWithResilienceWithDelay = TestBean.class.getMethod("doSomethingWithResilienceWithDelay", String.class, Integer.class);

        assertThatExceptionOfType(ExecutionException.class).isThrownBy(() ->
                Proxy.getInvocationHandler(testProxy).invoke(testProxy, doSomethingWithResilienceWithDelay, new Object[]{input, delay}));

        String cbTag = "testBean.doSomethingWithResilienceWithDelay.timeLimiter";
        assertThat(meterRegistry.get("resilience4j.timelimiter.calls")
                .tags("name", cbTag, "kind", "timeout")
                .counter().count()).isEqualTo(1);

    }

    private static class TestBean {
        @Resilience("testUpStream")
        public Object doSomethingWithResilience(String arg) {
            return arg;
        }

        @Resilience("testUpStream")
        public Object doSomethingWithResilienceWithDelay(String arg, Integer delayInMs) {
            try {
                Thread.sleep(delayInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return arg;
        }

        public Object doSomethingWithoutResilience(String arg) {
            return arg;
        }
    }


}