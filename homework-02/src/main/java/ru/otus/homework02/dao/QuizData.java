package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Question;

public interface QuizData {
    void prepareData();
    boolean hasNext();
    Question getNextQuestion();
}
