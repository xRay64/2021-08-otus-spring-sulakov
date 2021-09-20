package ru.otus.homeworklibrary.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homeworklibrary.domain.Author;

import java.util.List;

@JdbcTest
@DisplayName("DAO для работы с авторами")
@Import(AuthorDAODB.class)
class AuthorDAODBTest {
    @Autowired
    private AuthorDAO authorDAO;

    @Test
    @DisplayName("должен возвращать ожидаемое количество авторов из БД")
    void shouldReturnExpectiedAuthorCcount() {
        Assertions.assertThat(authorDAO.count())
                .isEqualTo(5);
    }

    @Test
    @DisplayName("должен возвращать ожидаемого автора по его ID")
    void shouldReturnExpectedAuthorById() {
        Author author = authorDAO.getById(1L);
        Assertions.assertThat(author)
                .usingRecursiveComparison()
                .isEqualTo(new Author(1, "Author1"));
    }

    @Test
    @DisplayName("должен возвращать список всех авторов")
    void shouldReturnAllAuthors() {
        List<Author> authorList = authorDAO.getAll();
        Assertions.assertThat(authorList)
                .hasSize(5)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new Author(1L, "Author1"),
                        new Author(2L, "Author2"),
                        new Author(3L, "Author3"),
                        new Author(4L, "Author4"),
                        new Author(5L, "Author5")
                        );
    }

    @Test
    @DisplayName("должен вставлять автора в БД")
    void shouldInsertAuthor() {
        long newAuthorId = authorDAO.insert(new Author(6L, "Author6"));
        Author insertedAuthr = authorDAO.getById(newAuthorId);
        Assertions.assertThat(newAuthorId)
                .isEqualTo(6L);
        Assertions.assertThat(insertedAuthr)
                .usingRecursiveComparison()
                .isEqualTo(new Author(6L, "Author6"));
    }

    @Test
    @DisplayName("должен обновлять автора в БД")
    void shouldUpdateAuthor() {
        authorDAO.update(new Author(5L, "AuthorUpdated"));
        Author updatedAuthor = authorDAO.getById(5L);
        Assertions.assertThat(updatedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(new Author(5L, "AuthorUpdated"));
    }

    @Test
    @DisplayName("должен удалять автора из БД")
    void shouldDeleteAuthor() {
        authorDAO.delete(new Author(2L, "Author2"));
        Assertions.assertThatThrownBy(() -> authorDAO.getById(2L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен удалять автора из БД по его ID")
    void shouldDeleteAuthorById() {
        authorDAO.deleteById(1L);
        Assertions.assertThatThrownBy(() -> authorDAO.getById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}