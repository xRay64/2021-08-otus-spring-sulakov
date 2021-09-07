package ru.otus.homework01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework01.helper.StringParser;

@Configuration
public class AppConfig {

    @Bean
    StringParser stringParser() {
        return new StringParser();
    }
}
