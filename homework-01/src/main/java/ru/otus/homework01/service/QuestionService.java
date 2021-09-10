package ru.otus.homework01.service;

import ru.otus.homework01.domain.Question;

public interface QuestionService {
    boolean hasNext();
    Question getNextQuestion();
}
