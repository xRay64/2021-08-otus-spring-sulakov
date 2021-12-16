package ru.otus.library.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.library.models.Author;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@DisplayName("Author repository")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private final List<Author> authorList = Arrays.asList(
            new Author("id-test1", "test Author1"),
            new Author("id-test2", "test Author2"),
            new Author("id-test3", "test Author3")
    );

    @Test
    @DisplayName("should save new Author")
    void shouldSaveAuthor() {
        Mono<Author> test_author = authorRepository.save(new Author("Id-test1", "test Author"));

        StepVerifier
                .create(test_author)
                .assertNext(author -> {
                    assertNotNull(author.getId());
                    assertEquals(author.getName(), "test Author");
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("should find all authors")
    void shouldFindAll() {
        Flux<Author> subscribe = authorRepository.saveAll(authorList);

        StepVerifier
                .create(subscribe)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("should find author by id")
    void shouldFindById() {
        Flux<Author> authorFlux = authorRepository.saveAll(authorList);

        StepVerifier
                .create(authorFlux)
                .expectNextCount(3)
                .verifyComplete();

        Mono<Author> subscribe = authorRepository.findById("id-test1");

        StepVerifier
                .create(subscribe)
                .assertNext(author -> {
                    assertEquals(author.getId(), "id-test1");
                    assertEquals(author.getName(), "test Author1");
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("should delete Author bu id")
    void shouldDeleteById() {
        Flux<Author> authorFlux = authorRepository.saveAll(authorList);

        StepVerifier
                .create(authorFlux)
                .expectNextCount(3)
                .verifyComplete();

        Mono<Void> deleteSubscribe = authorRepository.deleteById("id-test1");

        StepVerifier
                .create(deleteSubscribe)
                .verifyComplete();

        Mono<Author> findDeletedAuthor = authorRepository.findById("id-test1");

        StepVerifier
                .create(findDeletedAuthor)
                .verifyComplete();
    }
}