package ru.otus.homework01.dao;

import ru.otus.homework01.domain.Question;

public interface QuizDAO {
    int getStringsCount();
    String getString(int index);
}
