package ru.otus.homework01.helper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ru.otus.homework01.domain.Question;

class StringParserTest {
    StringParser stringParser = new StringParser();

    @Test
    void shouldCorrectConvertsToString() {
        String stringForParse = "Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;*Франклин Рузвельт;Рональд Рейган";
        Question question = stringParser.parseStringToQuestion(stringForParse);

        assertThat(question.toString())
                .isNotNull()
                .isEqualTo("The question is:\n" +
                        "Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?\n" +
                        "And here are responses:\n" +
                        "1. Джон Кеннеди\n" +
                        "2. Франклин Рузвельт\n" +
                        "3. Рональд Рейган\n");
    }

    @Test
    void shouldReturnsQuestion() {
        String stringForParse = "Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;*Франклин Рузвельт;Рональд Рейган";
        Question question = stringParser.parseStringToQuestion(stringForParse);

        assertThat(question.getQuestionText())
                .isNotNull()
                .isEqualTo("Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?");
    }

    @Test
    void shouldChecksRightAnswer() {
        String stringForParse = "Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;*Франклин Рузвельт;Рональд Рейган";
        Question question = stringParser.parseStringToQuestion(stringForParse);

        assertThat(question.checkResponseIndex(2))
                .isTrue();

        assertThat(question.checkResponseIndex(1))
                .isFalse();

        assertThat(question.checkResponseIndex(3))
                .isFalse();
    }
}