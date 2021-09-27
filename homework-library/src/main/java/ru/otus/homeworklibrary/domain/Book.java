package ru.otus.homeworklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
    private final long id;
    private String name;
    private Author author;
    private List<Genre> genreList;
}
