package ru.otus.homework02.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework02.helper.IOService;
import ru.otus.homework02.helper.IOStreamsProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("UserInteractionServiceImplTest Class")
class UserInteractionServiceImplTest {
    @MockBean
    IOStreamsProvider ioStreamsProvider;

    @Autowired
    IOService ioService;

    @DisplayName("should prompt a user")
    @Test
    void shouldPromptUser() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Mockito.when(ioStreamsProvider.getPrintStream())
                .thenReturn(new PrintStream(bos));
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        userInteractionService.promptUser("test string");

        assertThat(bos.toString()).isEqualTo("test string\n");
    }

    @Test
    void askUserForSting() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("Ivan Ivanov".getBytes());
        Mockito.when(ioStreamsProvider.getPrintStream())
                .thenReturn(new PrintStream(bos));
        Mockito.when(ioStreamsProvider.getInputStream())
                .thenReturn(bais);
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        String userAnswer = userInteractionService.askUserForString("ask user");
        assertThat(bos.toString()).isEqualTo("ask user\n");
        assertThat(userAnswer).isEqualTo("Ivan Ivanov");
    }

    @Test
    void askUserForInt() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream("2".getBytes());
        Mockito.when(ioStreamsProvider.getPrintStream())
                .thenReturn(new PrintStream(bos));
        Mockito.when(ioStreamsProvider.getInputStream())
                .thenReturn(bais);
        UserInteractionServiceImpl userInteractionService = new UserInteractionServiceImpl(ioService);
        int userAnswer = userInteractionService.askUserForInt("ask user for integer");
        assertThat(bos.toString()).isEqualTo("ask user for integer\n");
        assertThat(userAnswer).isEqualTo(2);
    }
}