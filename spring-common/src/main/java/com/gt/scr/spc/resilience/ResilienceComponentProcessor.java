package com.gt.scr.spc.resilience;

import com.gt.scr.resilience.Resilience;
import com.gt.scr.spc.config.ResilienceConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedThreadPoolBulkheadMetrics;
import io.github.resilience4j.micrometer.tagged.TaggedTimeLimiterMetrics;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

@Component
public class ResilienceComponentProcessor implements BeanPostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ResilienceComponentProcessor.class);

    private final Map<String, Class<?>> beanMap = new HashMap<>();
    private final Map<String, String> methodMap = new HashMap<>();
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry;
    private final TimeLimiterRegistry timeLimiterRegistry;
    private final ScheduledExecutorService scheduledExecutorService;

    public ResilienceComponentProcessor(ResilienceConfig resilienceConfig,
                                        MeterRegistry meterRegistry,
                                        @Qualifier("resilienceExecutorService") ScheduledExecutorService scheduledExecutorService) {
        circuitBreakerRegistry = resilienceConfig.circuitBreakerRegistry();
        threadPoolBulkheadRegistry = resilienceConfig.threadPoolBulkHeadRegistry();
        timeLimiterRegistry = resilienceConfig.timeLimiterRegistry();
        this.scheduledExecutorService = scheduledExecutorService;
        TaggedCircuitBreakerMetrics.ofCircuitBreakerRegistry(circuitBreakerRegistry).bindTo(meterRegistry);
        TaggedThreadPoolBulkheadMetrics.ofThreadPoolBulkheadRegistry(threadPoolBulkheadRegistry).bindTo(meterRegistry);
        TaggedTimeLimiterMetrics.ofTimeLimiterRegistry(timeLimiterRegistry).bindTo(meterRegistry);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Resilience.class))
                .forEach(method -> {
                    Resilience annotation = method.getAnnotation(Resilience.class);
                    beanMap.put(beanName, bean.getClass());
                    methodMap.put(beanName + "." + method.getName(), annotation.value());
                });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = beanMap.get(beanName);

        if (aClass != null) {
            return Proxy.newProxyInstance(aClass.getClassLoader(),
                    aClass.getInterfaces(),
                    (proxy, method, args) -> {
                        String key = beanName + "." + method.getName();
                        if (methodMap.containsKey(key)) {
                            String resilienceKey = methodMap.get(key);
                            Supplier<Object> supplier = () -> {
                                try {
                                    return method.invoke(bean, args);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    LOG.error(e.getMessage(), e);
                                }
                                return null;
                            };

                            Decorators.DecorateSupplier<Object> targetObjectSupplier = Decorators.ofSupplier(supplier);
                            Decorators.DecorateCompletionStage<Object> resilienceDecorator = getResilienceDecorator(key, resilienceKey,
                                    targetObjectSupplier);

                            LOG.info("Calling using resilience 4j");
                            return resilienceDecorator.get().toCompletableFuture().get();
                        }

                        return method.invoke(bean, args);
                    });
        }

        return bean;
    }

    private Decorators.DecorateCompletionStage<Object> getResilienceDecorator(
            String methodKey,
            String key,
            Decorators.DecorateSupplier<Object> finalObjectDecorateSupplier) {
        var threadPoolBulkhead = threadPoolBulkheadRegistry.bulkhead(methodKey + ".bulkhead", key);
        Decorators.DecorateCompletionStage<Object> objectDecorateCompletionStage =
                finalObjectDecorateSupplier.withThreadPoolBulkhead(threadPoolBulkhead);

        var circuitBreaker = circuitBreakerRegistry.circuitBreaker(methodKey + ".circuitBreaker", key);
        objectDecorateCompletionStage = objectDecorateCompletionStage.withCircuitBreaker(circuitBreaker);

        var timeLimiter = timeLimiterRegistry.timeLimiter(methodKey + ".timeLimiter", key);
        return objectDecorateCompletionStage.withTimeLimiter(timeLimiter, scheduledExecutorService);
    }
}
