package ru.otus.homeworkintegration.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.stereotype.Component;

@Component
public class Poller {
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }
}
