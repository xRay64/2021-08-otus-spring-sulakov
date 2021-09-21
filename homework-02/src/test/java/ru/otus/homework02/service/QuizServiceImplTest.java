package ru.otus.homework02.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.helper.IOService;
import ru.otus.homework02.helper.IOServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuizServiceImpl")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class QuizServiceImplTest {

    private static ByteArrayOutputStream bos =new ByteArrayOutputStream();

    @TestConfiguration
    static class NestedTestConfiguration{
        @Bean
        public IOService ioService() {
            return new IOServiceImpl(new PrintStream(bos), System.in);
        }
    }

    @MockBean
    QuizConfig quizConfig;

    @MockBean
    QuestionService questionService;

    @Autowired
    QuizService quizService;

    @DisplayName("должен выводить все вопросы")
    @Test
    void shouldPrintAllQuestions() {
        Mockito.when(quizConfig.getQuiz())
                .thenReturn(Mockito.mock(QuizConfig.Quiz.class));
        Mockito.when(quizConfig.getCsv())
                .thenReturn(Mockito.mock(QuizConfig.CSV.class));
        Mockito.when(quizConfig.getQuiz().getScoreToWin())
                .thenReturn(5);
        Mockito.when(quizConfig.getCsv().getFileName())
                .thenReturn("questions.csv");

        final int[] counterOfInvocation = {0};
        Mockito.when(questionService.hasNext()).thenAnswer(
                invocation -> counterOfInvocation[0]++ < 3);

        Question question = new Question("Test question");
        question.addResponse("1. One");
        question.addResponse("2. Two");
        question.addResponse("3. Three");
        Mockito.when(questionService.getNextQuestion())
                .thenReturn(question);

        quizService.printAllQuestions();

        assertThat(bos.toString())
                .isEqualTo("The question is:\n" +
                        "Test question\n" +
                        "And here are responses:\n" +
                        "1. 1. One\n" +
                        "2. 2. Two\n" +
                        "3. 3. Three\n" +
                        "\n" +
                        "----------------------------------------\n" +
                        "The question is:\n" +
                        "Test question\n" +
                        "And here are responses:\n" +
                        "1. 1. One\n" +
                        "2. 2. Two\n" +
                        "3. 3. Three\n" +
                        "\n" +
                        "----------------------------------------\n" +
                        "The question is:\n" +
                        "Test question\n" +
                        "And here are responses:\n" +
                        "1. 1. One\n" +
                        "2. 2. Two\n" +
                        "3. 3. Three\n" +
                        "\n" +
                        "----------------------------------------\n");
    }
}