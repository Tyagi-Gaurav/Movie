package com.gt.scr.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ConfigurationProperties("accessible")
@ConstructorBinding
public record AccessibleEndpointConfig(Map<String, Boolean> endpoints,
                                       Map<String, Boolean> endpointsRegex) {

    public boolean isEnabled(String method, String path) {
        return endpoints.getOrDefault(String.format("%s-%s", method, path), false);
    }

    @Override
    public Map<String, Boolean> endpoints() {
        return Collections.unmodifiableMap(endpoints);
    }

    @Override
    public Map<String, Boolean> endpointsRegex() {
        return Collections.unmodifiableMap(endpointsRegex);
    }

    public boolean satisfiesRegex(String method, String path) {
        String toMatch = String.format("%s-%s", method, path);
        return endpointsRegex.keySet().stream().map(Pattern::compile)
                .map(p -> p.matcher(toMatch))
                .anyMatch(Matcher::matches);
    }
}
