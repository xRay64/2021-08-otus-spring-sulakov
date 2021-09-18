package ru.otus.homework02.exceptions;

public class StringParserQuizRuntimeException extends QuizRuntimeException {
    public StringParserQuizRuntimeException(String message) {
        super(message);
    }

    public StringParserQuizRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
