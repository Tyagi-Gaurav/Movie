package com.gt.scr.spc.resilience;

import com.gt.scr.resilience.Resilience;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResilienceComponentProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> beanMap = new HashMap<>();
    private final Map<String, Class<?>> methodMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Resilience.class))
                .forEach(method -> {
                    beanMap.put(beanName, bean.getClass());
                    methodMap.put(beanName+"."+method.getName(), bean.getClass());
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
                        if (methodMap.containsKey(beanName+"."+method.getName())) {
                            //LOG.info("Wrap call to {} in Resilience", beanName + "." + method.getName());
                        }

                        return method.invoke(bean, args);
                    });
        }

        return bean;
    }
}
