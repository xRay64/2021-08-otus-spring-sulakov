package ru.otus.homeworklibrary.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homeworklibrary.models.Author;

import java.util.List;

@DataJpaTest
@DisplayName("DAO для работы с авторами")
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    private static final int EXPECTED_ALL_AUTHORS_COUNT = 5;

    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long SECOND_AUTHOR_ID = 2L;
    private static final long THIRD_AUTHOR_ID = 3L;
    private static final long FOURTH_AUTHOR_ID = 4L;
    private static final long FIFTH_AUTHOR_ID = 5L;
    private static final long SIX_AUTHOR_ID = 6L;

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен возвращать ожидаемое количество авторов из БД")
    void shouldReturnExpectedAuthorCount() {
        Assertions.assertThat(repository.count())
                .isEqualTo(EXPECTED_ALL_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("должен возвращать ожидаемого автора по его ID")
    void shouldReturnExpectedAuthorById() {
        Author authorToAssert = em.find(Author.class, FIRST_AUTHOR_ID);
        Author author = repository.find(FIRST_AUTHOR_ID).get();
        Assertions.assertThat(author)
                .usingRecursiveComparison()
                .isEqualTo(authorToAssert);
    }

    @Test
    @DisplayName("должен возвращать список всех авторов")
    void shouldReturnAllAuthors() {
        List<Author> authorList = repository.findAll();
        Assertions.assertThat(authorList)
                .hasSize(EXPECTED_ALL_AUTHORS_COUNT)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new Author(FIRST_AUTHOR_ID, "Author1"),
                        new Author(SECOND_AUTHOR_ID, "Author2"),
                        new Author(THIRD_AUTHOR_ID, "Author3"),
                        new Author(FOURTH_AUTHOR_ID, "Author4"),
                        new Author(FIFTH_AUTHOR_ID, "Author5")
                );
    }

    @Test
    @DisplayName("должен вставлять автора в БД")
    void shouldInsertAuthor() {
        long newAuthorId = repository.save(new Author(SIX_AUTHOR_ID, "Author6")).getId();
        em.clear();
        Author insertedAuthor = em.find(Author.class, SIX_AUTHOR_ID);
        Assertions.assertThat(newAuthorId)
                .isEqualTo(SIX_AUTHOR_ID);
        Assertions.assertThat(insertedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(new Author(SIX_AUTHOR_ID, "Author6"));
    }

    @Test
    @DisplayName("должен обновлять автора в БД")
    void shouldUpdateAuthor() {
        repository.updateNameById(FIFTH_AUTHOR_ID, "AuthorUpdated");
        em.flush();
        Author updatedAuthor = em.find(Author.class, FIFTH_AUTHOR_ID);
        Assertions.assertThat(updatedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(new Author(FIFTH_AUTHOR_ID, "AuthorUpdated"));
    }

    @Test
    @DisplayName("должен удалять автора из БД по его ID")
    void shouldDeleteAuthorById() {
        Author deletingAuthor = em.find(Author.class, SECOND_AUTHOR_ID);
        Assertions.assertThat(deletingAuthor).isNotNull();
        repository.deleteById(SECOND_AUTHOR_ID);
        em.flush();
        Assertions.assertThat(repository.find(SECOND_AUTHOR_ID))
                .isEmpty();
    }
}