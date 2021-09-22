package ru.otus.homework02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.otus.homework02.helper.IOService;
import ru.otus.homework02.helper.IOServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("UserInteractionServiceImplTest Class")
@Execution(ExecutionMode.CONCURRENT)
class UserInteractionServiceImplTest {

    @BeforeEach
    void setUp() {
        System.out.println(Thread.currentThread().getName());
    }

    @DisplayName("should prompt a user")
    @Test
    void shouldPromptUser() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService ioService = new IOServiceImpl(new PrintStream(bos), System.in);
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        userInteractionService.promptUser("test string");

        assertThat(bos.toString()).isEqualTo("test string\n");
    }

    @Test
    void askUserForSting() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("Ivan Ivanov".getBytes());
        IOService ioService = new IOServiceImpl(new PrintStream(bos), bais);
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        String userAnswer = userInteractionService.askUserForString("ask user");
        assertThat(bos.toString()).isEqualTo("ask user\n");
        assertThat(userAnswer).isEqualTo("Ivan Ivanov");
    }

    @Test
    void askUserForInt() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("2".getBytes());
        IOService ioService = new IOServiceImpl(new PrintStream(bos), bais);
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        int userAnswer = userInteractionService.askUserForInt("ask user for integer");
        assertThat(bos.toString()).isEqualTo("ask user for integer\n");
        assertThat(userAnswer).isEqualTo(2);
    }
}