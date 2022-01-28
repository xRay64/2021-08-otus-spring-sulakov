package ru.otus.homeworkintegration.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.stereotype.Component;

@Component
public class Channels {
    @Bean
    public QueueChannel commitsChanel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public DirectChannel buildChanel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel deployLogsChanel() {
        return MessageChannels.publishSubscribe().get();
    }
}
