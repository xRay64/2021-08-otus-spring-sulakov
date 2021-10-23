package ru.otus.homeworklibrary.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<BookComment, Long> {
    @EntityGraph(attributePaths = "book")
    List<BookComment> findAll();
}
