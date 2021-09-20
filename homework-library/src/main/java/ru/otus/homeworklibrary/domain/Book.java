package ru.otus.homeworklibrary.domain;

import java.util.List;

public class Book {
    private final long id;
    private final String name;
    private final List<Genre> genreList;

    public Book(long id, String name, List<Genre> genreList) {
        this.id = id;
        this.name = name;
        this.genreList = genreList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }
}
