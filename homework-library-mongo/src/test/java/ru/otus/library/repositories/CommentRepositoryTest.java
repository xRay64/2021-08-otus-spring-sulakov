package ru.otus.library.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;

import java.util.List;

@DataMongoTest
@DisplayName("CommentRepository должен")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    public static final String FIRST_BOOK_ID = "book-001";
    public static final int EXPECTED_COMMENT_COUNT = 2;
    public static final int EXPECTED_COMMENT_COUNT_AFTER_DELETING = 0;

    @Test
    @DisplayName("возвращать список комментариев к книге")
    void shouldReturnBookComments() {
        Book book = bookRepository.findById(FIRST_BOOK_ID).get();

        List<Comment> commentByBook = commentRepository.getCommentByBook(book);

        Assertions.assertThat(commentByBook)
                .isNotNull()
                .hasSize(EXPECTED_COMMENT_COUNT);

        Assertions.assertThat(commentByBook)
                .extracting(Comment::getId)
                .containsExactlyInAnyOrder("comment-001", "comment-002");

        Assertions.assertThat(commentByBook)
                .extracting(Comment::getText)
                .containsExactlyInAnyOrder("comment text 1 for book-001", "comment text 2 for book-001");
    }

    @Test
    @DisplayName("должен удалять комментарии по Id книги")
    void shouldDeleteCommentsByBookId() {
        Book firstBook = bookRepository.findById(FIRST_BOOK_ID).get();

        List<Comment> commentListBeforeDeleting = commentRepository.getCommentByBook(firstBook);

        Assertions.assertThat(commentListBeforeDeleting)
                .hasSize(EXPECTED_COMMENT_COUNT);

        commentRepository.deleteByBook_Id(FIRST_BOOK_ID);

        List<Comment> commentListAfterDeleting = commentRepository.getCommentByBook(firstBook);

        Assertions.assertThat(commentListAfterDeleting)
                .hasSize(EXPECTED_COMMENT_COUNT_AFTER_DELETING);

        //восстановим состояние БД
        commentRepository.saveAll(commentListBeforeDeleting);
    }
}