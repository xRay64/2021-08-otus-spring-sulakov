package ru.otus.homework01.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework01.dao.QuizData;

@PropertySource("classpath:application.properties")
@DisplayName("Класс QuizServiceImpl")
class QuizServiceImplTest {

    @Value("${quiz.score-to-vin}")
    private int scoreToWin;

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        QuizData quizData = Mockito.mock(QuizData.class);
        UserInteractionService userInteractionService = Mockito.mock(UserInteractionServiceImpl.class);
        QuizServiceImpl quizService = new QuizServiceImpl(quizData, userInteractionService, scoreToWin);
        int i = 0;
        Mockito.when(quizData.hasNext())
                .thenReturn(true);
        //TODO доделать тест когда поумнею
    }
}