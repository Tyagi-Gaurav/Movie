package com.gt.scr.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collections;
import java.util.Set;

@ConfigurationProperties("toggle")
@ConstructorBinding
public record ToggleConfig(Set<String> endpoints) {

    public boolean contains(String method, String path) {
        return endpoints.contains(String.format("%s-%s", method, path));
    }

    @Override
    public Set<String> endpoints() {
        return Collections.unmodifiableSet(endpoints);
    }
}
