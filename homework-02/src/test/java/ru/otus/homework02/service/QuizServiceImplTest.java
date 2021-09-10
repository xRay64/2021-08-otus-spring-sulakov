package ru.otus.homework02.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.homework02.dao.QuizData;

@DisplayName("Класс QuizServiceImpl")
class QuizServiceImplTest {

    private QuizServiceImpl quizService;

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        QuizData quizData = Mockito.mock(QuizData.class);
        UserInteractionService userInteractionService = Mockito.mock(UserInteractionServiceImpl.class);
        int i = 0;
        Mockito.when(quizData.hasNext())
                .thenReturn(true);
        //TODO доделать тест когда поумнею
    }
}