package ru.otus.homeworklibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworklibrary.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
