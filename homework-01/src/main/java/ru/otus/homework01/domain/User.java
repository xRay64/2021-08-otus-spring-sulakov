package ru.otus.homework01.domain;

public class User {

    private final String userName;
    private int score;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return this.score;
    }

    public void upScore() {
        this.score++;
    }
}
