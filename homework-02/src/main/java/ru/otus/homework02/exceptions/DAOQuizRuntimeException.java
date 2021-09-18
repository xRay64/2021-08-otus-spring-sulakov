package ru.otus.homework02.exceptions;

public class DAOQuizRuntimeException extends QuizRuntimeException {
    public DAOQuizRuntimeException(String message) {
        super(message);
    }

    public DAOQuizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
