package ru.otus.homeworkbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworkbatch.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
