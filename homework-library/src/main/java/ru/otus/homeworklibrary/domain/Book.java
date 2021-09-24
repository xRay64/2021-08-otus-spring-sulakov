package ru.otus.homeworklibrary.domain;

import java.util.List;

public class Book {
    private final long id;
    private String name;
    private Author author;
    private List<Genre> genreList;

    public Book(long id, String name, Author author, List<Genre> genreList) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genreList = genreList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("    book id   : ").append(this.id).append("\n");
        sb.append("    book name : ").append(this.name).append("\n");
        sb.append("    autor     : ").append(author).append("\n");
        sb.append("    genreList : ");
        for (Genre genre : this.genreList) {
            sb.append(genre);
        }
        sb.append("\n");
        sb.append("\n}");
        return sb.toString();
    }
}
