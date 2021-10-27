package ru.otus.library.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;

import java.util.List;

@DataMongoTest
@DisplayName("BookRepository должен")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private final static int EXPECTED_BOOKS_COUNT = 5;
    private final static int EXPECTED_AUTHORS_COUNT = 4;
    private final static int EXPECTED_GENRES_COUNT = 4;

    @Test
    @DisplayName("возвращать ожидаемое количество книг")
    void shouldReturnCount() {
        Assertions.assertThat(bookRepository.count())
                .isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("возвращать список всех авторов")
    void shouldReturnAuthorsList() {
        List<Author> allAuthors = bookRepository.findAllAuthors();

        Assertions.assertThat(allAuthors)
                .isNotNull()
                .hasSize(EXPECTED_AUTHORS_COUNT)
                .contains(new Author("author-001", "Test author 1"), new Author("author-004", "Test author 4"));
    }

    @Test
    @DisplayName("возвращать список всех жанров")
    void shouldReturnGenresList() {
        List<Genre> allGenres = bookRepository.findAllGenres();

        Assertions.assertThat(allGenres)
                .isNotNull()
                .hasSize(EXPECTED_GENRES_COUNT)
                .contains(new Genre("genre-001", "Test genre 1"), new Genre("genre-004", "Test genre 4"));
    }
}