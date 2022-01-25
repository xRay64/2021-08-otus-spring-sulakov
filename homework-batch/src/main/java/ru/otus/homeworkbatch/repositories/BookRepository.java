package ru.otus.homeworkbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworkbatch.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
