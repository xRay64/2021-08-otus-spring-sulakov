package ru.otus.library.controllers.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class BookDTO {
    private final String name;
    private final List<Author> authors;
    private final List<Genre> genres;
}
