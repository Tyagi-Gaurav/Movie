package com.gt.scr.spc.resilience;

import com.gt.scr.resilience.Resilience;
import com.gt.scr.spc.config.ResilienceConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
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
import java.util.Optional;
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
    private final ScheduledExecutorService executorService;

    public ResilienceComponentProcessor(ResilienceConfig resilienceConfig,
                                        @Qualifier("ResilienceTimeLimiterScheduler") ScheduledExecutorService executorService) {
        circuitBreakerRegistry = resilienceConfig.circuitBreakerRegistry();
        threadPoolBulkheadRegistry = resilienceConfig.threadPoolBulkHeadRegistry();
        timeLimiterRegistry = resilienceConfig.timeLimiterRegistry();
        this.executorService = executorService;
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
                                    e.printStackTrace();
                                }
                                return null;
                            };

                            Decorators.DecorateSupplier<Object> objectDecorateSupplier1 = Decorators.ofSupplier(supplier);
                            Decorators.DecorateCompletionStage<Object> objectDecorateSupplier11 = getObjectDecorateSupplier1(resilienceKey, objectDecorateSupplier1);

                            LOG.info("Calling using resilience 4j");
                            return objectDecorateSupplier11.get().toCompletableFuture().get();
                        }

                        return method.invoke(bean, args);
                    });
        }

        return bean;
    }

    private Decorators.DecorateCompletionStage<Object> getObjectDecorateSupplier1(String key,
                                                                                  Decorators.DecorateSupplier<Object> finalObjectDecorateSupplier) {
        Optional<ThreadPoolBulkhead> threadPoolBulkhead = threadPoolBulkheadRegistry.find(key);
        if (threadPoolBulkhead.isPresent()) {
            Decorators.DecorateCompletionStage<Object> objectDecorateCompletionStage =
                    finalObjectDecorateSupplier.withThreadPoolBulkhead(threadPoolBulkhead.get());

            Optional<CircuitBreaker> circuitBreaker = circuitBreakerRegistry.find(key);
            if (circuitBreaker.isPresent()) {
                objectDecorateCompletionStage = objectDecorateCompletionStage.withCircuitBreaker(circuitBreaker.get());
            }

            return objectDecorateCompletionStage;
        } else {
            return finalObjectDecorateSupplier.withThreadPoolBulkhead(threadPoolBulkheadRegistry.bulkhead(key));
        }
    }
}
