package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
