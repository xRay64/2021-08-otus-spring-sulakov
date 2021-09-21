package ru.otus.homework02.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuizMessageSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("QuestionPrinterImplTest должен")
class QuestionPrinterImplTest {

    @MockBean
    QuizConfig quizConfig;

    @MockBean
    QuizMessageSource quizMessageSource;

    @DisplayName("Должен выводить вопрос без перевода")
    @Test
    void shouldPrintOriginalQuestion() {
        QuizConfig.CSV csv = Mockito.mock(QuizConfig.CSV.class);
        Mockito.when(quizConfig.getCsv())
                .thenReturn(csv);
        Mockito.when(quizConfig.getCsv().getFileName())
                .thenReturn("questions.csv");
        Mockito.when(quizConfig.getLanguage())
                .thenReturn("EN");

        Mockito.when(quizMessageSource.getMessage(Mockito.eq("string.question.is")))
                .thenReturn("The question is:\n");
        Mockito.when(quizMessageSource.getMessage(Mockito.eq("string.question.responses")))
                .thenReturn("The answers is:\n");

        Question question = Mockito.mock(Question.class);
        Mockito.when(question.getQuestionText())
                .thenReturn("TestQuestion1");
        Map<Integer, String> answersMap = new HashMap<>();
        answersMap.put(1, "Answer1");
        answersMap.put(2, "Answer2");
        answersMap.put(3, "Answer3");
        Mockito.when(question.getResponses())
                .thenReturn(answersMap);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("".getBytes());

        IOServiceImpl ioService = new IOServiceImpl(new PrintStream(bos), bais);

        QuestionPrinter questionPrinter = new QuestionPrinterImpl(quizConfig, quizMessageSource, ioService);
        questionPrinter.printQuestion(question);

        assertThat(bos.toString())
                .isEqualTo("The question is:\n" +
                        "TestQuestion1\n" +
                        "The answers is:\n" +
                        "1. Answer1\n" +
                        "2. Answer2\n" +
                        "3. Answer3\n\n");
    }

    @DisplayName("Должен выводить локализованный вопрос")
    @Test
    void shouldPrintLocalizedQuestion() {
        QuizConfig.CSV csv = Mockito.mock(QuizConfig.CSV.class);
        Mockito.when(quizConfig.getCsv())
                .thenReturn(csv);
        Mockito.when(quizConfig.getCsv().getFileName())
                .thenReturn("questions.csv");
        Mockito.when(quizConfig.getLanguage())
                .thenReturn("RU"); // Этот параметр запускает вывод локализованного вопроса

        Mockito.when(quizMessageSource.getMessage(Mockito.eq("string.question.is")))
                .thenReturn("Вопрос:\n");
        Mockito.when(quizMessageSource.getMessage(Mockito.eq("string.question.responses")))
                .thenReturn("Варианты ответов:\n");

        Question question = Mockito.mock(Question.class);
        Mockito.when(question.getQuestionTextTranslated())
                .thenReturn("Тестовый вопрос1");
        Map<Integer, String> answersMap = new HashMap<>();
        answersMap.put(1, "Ответ1");
        answersMap.put(2, "Ответ2");
        answersMap.put(3, "Ответ3");
        Mockito.when(question.getResponses())
                .thenReturn(answersMap);
        Mockito.when(question.getResponsesTranslated())
                .thenReturn(answersMap);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("".getBytes());

        IOServiceImpl ioService = new IOServiceImpl(new PrintStream(bos), bais);

        QuestionPrinter questionPrinter = new QuestionPrinterImpl(quizConfig, quizMessageSource, ioService);
        questionPrinter.printQuestion(question);

        assertThat(bos.toString())
                .isEqualTo("Вопрос:\n" +
                        "Тестовый вопрос1\n" +
                        "Варианты ответов:\n" +
                        "1. Ответ1\n" +
                        "2. Ответ2\n" +
                        "3. Ответ3\n\n");
    }
}