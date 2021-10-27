package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> getCommentByBook(Book book);

    void deleteByBook_Id(String bookId);
}
