package ru.otus.homework02.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Класс QuizServiceImpl")
class QuizServiceImplTest {

    private QuizServiceImpl quizService;

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        QuestionService questionService = Mockito.mock(QuestionServiceImpl.class);
        UserInteractionService userInteractionService = Mockito.mock(UserInteractionServiceImpl.class);
        int i = 0;
        Mockito.when(questionService.hasNext())
                .thenReturn(true);
        //TODO доделать тест когда поумнею
    }
}