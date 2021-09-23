package ru.otus.homework02.helper;

import ru.otus.homework02.domain.Question;

public interface StringToQuestionParser {
    Question parseStringToQuestion(String inputString);
}
