package com.gt.scr.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("filesystemcontentstore")
public record FileSystemContentStoreConfig(String contentStoreRoot) {
}
