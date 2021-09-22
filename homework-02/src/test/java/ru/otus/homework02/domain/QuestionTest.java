package ru.otus.homework02.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Question")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class QuestionTest {

    @DisplayName("должен сохранять и отдавать текст вопроса")
    @Test
    void shouldGetQuestionText() {
        Question q = new Question("question text");

        assertThat(q.getQuestionText())
                .isEqualTo("question text");
    }

    @DisplayName("должен проверять ответ")
    @Test
    void shouldCheckResponse() {
        Question q = new Question("question text");
        q.setRightResponseIndex(1);

        assertThat(q.checkResponseIndex(1))
                .isTrue();
    }

    @DisplayName("должен приобразовываться к строке")
    @Test
    void shouldConvertToString() {
        Question q = new Question("question text");
        q.addResponse("response one");
        q.addResponse("response two");
        q.addResponse("response three");
        q.addResponse("response four");

        assertThat(q.toString())
                .isEqualTo(
                        "The question is:\n" +
                        "question text\n" +
                        "And here are responses:\n" +
                        "1. response one\n" +
                        "2. response two\n" +
                        "3. response three\n" +
                        "4. response four\n");
    }

}