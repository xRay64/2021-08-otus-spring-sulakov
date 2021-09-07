package ru.otus.homework01.dao;

import ru.otus.homework01.domain.Question;

public interface QuizData {
    boolean hasNext();
    Question getNextQuestion();
}
