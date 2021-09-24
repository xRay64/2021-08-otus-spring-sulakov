package ru.otus.homeworklibrary.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

@JdbcTest
@DisplayName("DAO для работы с жанрами")
@Import(GenreDAODB.class)
class GenreDAODBTest {
    @Autowired
    private GenreDAO genreDAO;

    @Test
    @DisplayName("должен возвращать ожидаемое количество жанров из БД")
    void shouldReturnExpectiedAuthorCcount() {
        Assertions.assertThat(genreDAO.count())
                .isEqualTo(3);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый жанров по его ID")
    void shouldReturnExpectedAuthorById() {
        Genre genre = genreDAO.getById(1L);
        Assertions.assertThat(genre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(1, "Genre1"));
    }

    @Test
    @DisplayName("должен возвращать список всех жанров")
    void shouldReturnAllAuthors() {
        List<Genre> genreList = genreDAO.getAll();
        Assertions.assertThat(genreList)
                .hasSize(3)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new Genre(1L, "Genre1"),
                        new Genre(2L, "Genre2"),
                        new Genre(3L, "Genre3")
                );
    }

    @Test
    @DisplayName("должен вставлять жанр в БД")
    void shouldInsertAuthor() {
        long newGenreId = genreDAO.insert(new Genre(4L, "Genre4"));
        Genre insertedGenre = genreDAO.getById(newGenreId);
        Assertions.assertThat(newGenreId)
                .isEqualTo(4L);
        Assertions.assertThat(insertedGenre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(4L, "Genre4"));
    }

    @Test
    @DisplayName("должен обновлять жанр в БД")
    void shouldUpdateAuthor() {
        genreDAO.update(new Genre(3L, "GenreUpdated"));
        Genre updatedGenre = genreDAO.getById(3L);
        Assertions.assertThat(updatedGenre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(3L, "GenreUpdated"));
    }

    @Test
    @DisplayName("должен удалять жанр из БД")
    void shouldDeleteAuthor() {
        genreDAO.delete(new Genre(2L, "Genre2"));
        Assertions.assertThatThrownBy(() -> genreDAO.getById(2L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен удалять жанр из БД по его ID")
    void shouldDeleteAuthorById() {
        genreDAO.deleteById(1L);
        Assertions.assertThatThrownBy(() -> genreDAO.getById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен возвращать корректный максимальный id")
    void shouldReturnCorrectMaxID() {
        Assertions.assertThat(genreDAO.getMaxId())
                .isEqualTo(3);
    }
}