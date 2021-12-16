package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
