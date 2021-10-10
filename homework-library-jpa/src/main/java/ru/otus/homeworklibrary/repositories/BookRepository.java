package ru.otus.homeworklibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworklibrary.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
}
