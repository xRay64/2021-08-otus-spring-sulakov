package ru.otus.configgenerator.exception;

public class ConfigGeneratorException extends RuntimeException {
    public ConfigGeneratorException(String message) {
        super(message);
    }

    public ConfigGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
