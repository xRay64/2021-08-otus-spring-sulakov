package ru.otus.homework02.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserInteractionServiceImplTest Class")
class UserInteractionServiceImplTest {
    PrintStream originalOutStream = System.out;
    InputStream originalInStream = System.in;
    ByteArrayOutputStream mock = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(mock));
    }

    @DisplayName("should prompt a user")
    @Test
    void shouldPromptUser() {
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl();
        userInteractionService.promptUser("test string");

        assertThat(mock.toString().trim()).isEqualTo("test string");
    }

    @Test
    void askUserForSting() {
        System.setIn(new ByteArrayInputStream("Ivan Ivanov".getBytes()));
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl();
        String userAnswer = userInteractionService.askUserForString("ask user");
        assertThat(mock.toString().trim()).isEqualTo("ask user");
        assertThat(userAnswer).isEqualTo("Ivan Ivanov");
    }

    @Test
    void askUserForInt() {
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl();
        int userAnswer = userInteractionService.askUserForInt("ask user for integer");
        assertThat(mock.toString().trim()).isEqualTo("ask user for integer");
        assertThat(userAnswer).isEqualTo(2);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutStream);
        System.setIn(originalInStream);
    }
}