package ru.otus.homework02.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework02.service.QuizMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Question")
class QuestionTest {
    @Autowired
    private QuizMessageSource quizMessageSource;

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