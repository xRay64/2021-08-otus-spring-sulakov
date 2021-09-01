package ru.otus.homework01.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.homework01.dao.QuizData;

@DisplayName("Класс QuizServiceImpl")
class QuizServiceImplTest {

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        QuizData quizData = Mockito.mock(QuizData.class);
        QuizServiceImpl quizService = new QuizServiceImpl(quizData);
        int i = 0;
        Mockito.when(quizData.hasNext())
                .thenReturn(true);
        //TODO доделать тест когда поумнею
    }
}