package ru.otus.homework01.helper;

import ru.otus.homework01.domain.Question;

public final class StringParser {
    public static Question parseStringToQuestion(String inputString) {
        String[] strings = inputString.split(";");
        if (strings.length == 0) {
            throw new StringParserException("The inputString is empty");
        }
        Question question = new Question(strings[0]);

        if (strings.length >= 1)
        for (int i = 1; i < strings.length; i++) {
            question.addResponse(strings[i]);
        }

        return question;
    }
}
