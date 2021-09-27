package ru.otus.homeworklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private final long id;
    private String name;
}
