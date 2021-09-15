package ru.otus.homework02.service;

import ru.otus.homework02.domain.Question;

public interface QuestionService {
    boolean hasNext();
    Question getNextQuestion();
}
