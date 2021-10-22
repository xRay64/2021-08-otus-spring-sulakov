package ru.otus.homeworklibrary.services.dto;

import lombok.*;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.BookComment;
import ru.otus.homeworklibrary.models.Genre;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private List<Author> author;
    private List<Genre> genre;
    private List<BookComment> comments;
}
