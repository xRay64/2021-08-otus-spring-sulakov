package ru.otus.configgenerator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.hazelcast")
@Configuration
@Getter
@Setter
public class HazelcastCacheParams {
    private int timeToLive;
}
