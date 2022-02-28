package ru.otus.configgenerator.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedCacheConfig {
    Logger logger = LoggerFactory.getLogger(EmbeddedCacheConfig.class);

    @Autowired
    HazelcastCacheParams hazelcastCacheParams;

    @Bean
    Config config() {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("mavenPluginListConfig");
        mapConfig.setTimeToLiveSeconds(hazelcastCacheParams.getTimeToLive());
        logger.info("Hazelcast TTL time is set for {}", hazelcastCacheParams.getTimeToLive());
        config.getMapConfigs().put("mavenPluginList", mapConfig);

        return config;
    }
}