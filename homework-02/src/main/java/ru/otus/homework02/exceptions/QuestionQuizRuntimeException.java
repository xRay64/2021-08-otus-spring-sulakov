package ru.otus.homework02.exceptions;

public class QuestionQuizRuntimeException extends QuizRuntimeException {
    public QuestionQuizRuntimeException(String message) {
        super(message);
    }

    public QuestionQuizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
