package ru.otus.homework01.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework01.dao.QuizDAO;

@PropertySource("classpath:application.properties")
@DisplayName("Класс QuizServiceImpl")
class QuizServiceImplTest {

    @Value("${quiz.score-to-vin}")
    private int scoreToWin;

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        QuestionService questionService = Mockito.mock(QuestionService.class);
        UserInteractionService userInteractionService = Mockito.mock(UserInteractionServiceImpl.class);
        QuizServiceImpl quizService = new QuizServiceImpl(questionService, userInteractionService, scoreToWin);
        int i = 0;
        Mockito.when(questionService.hasNext())
                .thenReturn(true);
        //TODO доделать тест когда поумнею
    }
}