package ru.otus.homework02.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework02.config.QuizConfig;

import java.util.Locale;

@Service
public class QuizMessageSource {
    private final MessageSource messageSource;
    private final QuizConfig quizConfig;

    public QuizMessageSource(MessageSource messageSource, QuizConfig quizConfig) {
        this.messageSource = messageSource;
        this.quizConfig = quizConfig;
    }

    public String getMessage(String code, String... params) {
        return messageSource.getMessage(code, params, Locale.forLanguageTag(quizConfig.getLanguage()));
    }
}
