package ru.otus.homework02.service;

public interface UserInteractionService {
    void promptUser(String message);

    String askUserForString(String message);

    int askUserForInt(String message);
}
