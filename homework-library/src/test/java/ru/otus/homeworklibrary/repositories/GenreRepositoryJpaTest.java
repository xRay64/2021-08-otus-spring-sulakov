package ru.otus.homeworklibrary.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homeworklibrary.models.Genre;

import java.util.List;

@DataJpaTest
@DisplayName("DAO для работы с жанрами")
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    private static final int EXPECTED_ALL_GENRES_COUNT = 3;

    private static final long FIRST_GENRE_ID = 1L;
    private static final long SECOND_GENRE_ID = 2L;
    private static final long THIRD_GENRE_ID = 3L;
    private static final long FOURTH_GENRE_ID = 4L;

    @Autowired
    TestEntityManager em;

    @Autowired
    private GenreRepository repository;

    @Test
    @DisplayName("должен возвращать ожидаемое количество жанров из БД")
    void shouldReturnExpectingGenreCount() {
        Assertions.assertThat(repository.count())
                .isEqualTo(EXPECTED_ALL_GENRES_COUNT);
    }

    @Test
    @DisplayName("должен возвращать ожидаемый жанров по его ID")
    void shouldReturnExpectedGenreById() {
        Genre genre = repository.findById(FIRST_GENRE_ID).get();
        Assertions.assertThat(genre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(FIRST_GENRE_ID, "Genre1"));
    }

    @Test
    @DisplayName("должен возвращать список всех жанров")
    void shouldReturnAllGenre() {
        List<Genre> genreList = repository.findAll();
        Assertions.assertThat(genreList)
                .hasSize(EXPECTED_ALL_GENRES_COUNT)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new Genre(FIRST_GENRE_ID, "Genre1"),
                        new Genre(SECOND_GENRE_ID, "Genre2"),
                        new Genre(THIRD_GENRE_ID, "Genre3")
                );
    }

    @Test
    @DisplayName("должен вставлять жанр в БД")
    void shouldInsertGenre() {
        long newGenreId = repository.save(new Genre(FOURTH_GENRE_ID, "Genre4")).getId();
        em.flush();
        em.clear();
        Genre insertedGenre = em.find(Genre.class, FOURTH_GENRE_ID);
        Assertions.assertThat(newGenreId)
                .isEqualTo(FOURTH_GENRE_ID);
        Assertions.assertThat(insertedGenre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(FOURTH_GENRE_ID, "Genre4"));
    }

    @Test
    @DisplayName("должен обновлять жанр в БД")
    void shouldUpdateGenre() {
        repository.updateNameById(THIRD_GENRE_ID, "GenreUpdated");
        em.flush();
        Genre updatedGenre = em.find(Genre.class, THIRD_GENRE_ID);
        Assertions.assertThat(updatedGenre)
                .usingRecursiveComparison()
                .isEqualTo(new Genre(THIRD_GENRE_ID, "GenreUpdated"));
    }

    @Test
    @DisplayName("должен удалять жанр из БД по его ID")
    void shouldDeleteGenreById() {
        repository.deleteById(SECOND_GENRE_ID);
        em.flush();
        Assertions.assertThat(em.find(Genre.class, SECOND_GENRE_ID))
                        .isNull();
    }
}