package ru.otus.library.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

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
}