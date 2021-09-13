package ru.otus.homework02.helper;

import org.junit.jupiter.api.Test;
import ru.otus.homework02.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StringToQuestionParserTest {
    StringToQuestionParser stringToQuestionParser = new StringToQuestionParser();

    @Test
    void shouldCorrectConvertsToString() {
        String stringForParse = "An american president who has wrote own sherlock holmes story;John F. Kennedy;Franklin Roosevelt;Ronald Reagan;Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;Франклин Рузвельт;Рональд Рейган;2";
        Question question = stringToQuestionParser.parseStringToQuestion(stringForParse);

        assertThat(question.toString())
                .isNotNull()
                .isEqualTo("The question is:\n" +
                        "An american president who has wrote own sherlock holmes story\n" +
                        "And here are responses:\n" +
                        "1. John F. Kennedy\n" +
                        "2. Franklin Roosevelt\n" +
                        "3. Ronald Reagan\n");
    }

    @Test
    void shouldReturnsQuestion() {
        String stringForParse = "An american president who has wrote own sherlock holmes story;John F. Kennedy;Franklin Roosevelt;Ronald Reagan;Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;Франклин Рузвельт;Рональд Рейган;2";
        Question question = stringToQuestionParser.parseStringToQuestion(stringForParse);

        assertThat(question.getQuestionText())
                .isNotNull()
                .isEqualTo("An american president who has wrote own sherlock holmes story");
    }

    @Test
    void shouldChecksRightAnswer() {
        String stringForParse = "An american president who has wrote own sherlock holmes story;John F. Kennedy;Franklin Roosevelt;Ronald Reagan;Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;Франклин Рузвельт;Рональд Рейган;2";
        Question question = stringToQuestionParser.parseStringToQuestion(stringForParse);

        assertThat(question.checkResponseIndex(2))
                .isTrue();

        assertThat(question.checkResponseIndex(1))
                .isFalse();

        assertThat(question.checkResponseIndex(3))
                .isFalse();
    }

    @Test
    void shouldThrowExceptionOnEmptyString() {
        String stringToParse = "";
        assertThatExceptionOfType(StringParserException.class)
                .isThrownBy(() -> stringToQuestionParser.parseStringToQuestion(stringToParse))
                .withMessage("The inputString is empty");
    }

    @Test
    void shouldThrowExceptionOnErrorStringFormat() {
        String stringForParse = "Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?;Джон Кеннеди;Франклин Рузвельт;Рональд Рейган;2";
        assertThatExceptionOfType(StringParserException.class)
                .isThrownBy(() -> stringToQuestionParser.parseStringToQuestion(stringForParse))
                .withMessage("Strings in CSV file dose not match correct format");
    }
}