package ru.otus.homeworkbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworkbatch.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
