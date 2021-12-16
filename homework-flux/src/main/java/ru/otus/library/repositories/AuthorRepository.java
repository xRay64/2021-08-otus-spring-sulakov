package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.models.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
