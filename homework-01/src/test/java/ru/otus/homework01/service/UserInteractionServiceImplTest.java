package ru.otus.homework01.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserInteractionServiceImplTest Class")
class UserInteractionServiceImplTest {
    PrintStream originalOutStream = System.out;
    InputStream originalInStream = System.in;
    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(mock));
    }

    @DisplayName("should prompt a user")
    @Test
    void shouldPromptUser() {
        userInteractionService.promptUser("test string");

        assertThat(mock.toString().trim()).isEqualTo("test string");
    }

    @Test
    void askUserForSting() {
        userInteractionService.askUserForString("ask user");
        assertThat(mock.toString().trim()).isEqualTo("askUser");
        System.setIn(new ByteArrayInputStream("Ivan Ivanov".getBytes(StandardCharsets.UTF_8)));

    }

    @Test
    void askUserForInt() {
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutStream);
        System.setIn(originalInStream);
    }
}