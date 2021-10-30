package ru.otus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "books")
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String name;
    @DBRef
    private List<Author> authorList;
    @DBRef
    private List<Genre> genreList;
    private List<Comment> commentList;
}
