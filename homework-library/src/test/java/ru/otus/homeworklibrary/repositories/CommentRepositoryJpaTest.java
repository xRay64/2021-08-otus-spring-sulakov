package ru.otus.homeworklibrary.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;

import java.util.List;

@DisplayName("Repository для доступа к комментариям")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final int EXPECTED_COMMENT_LIST_SIZE = 4;

    private static final long FIRST_COMMENT_ID = 1L;
    private static final long SECOND_COMMENT_ID = 2L;
    private static final long THIRD_COMMENT_ID = 3L;
    private static final long FOURTH_COMMENT_ID = 4L;
    private static final long FIFTH_COMMENT_ID = 5L;

    private static final String TEST_COMMENT_TEXT = "test comment text";

    private static final int EXPECTED_ALL_COMMENTS_COUNT = 4;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepositoryJpa repository;

    @DisplayName("должен возвращать определенное количество комментариев из БД")
    @Test
    void shouldReturnExpectedCommentCount() {
        Assertions.assertThat(repository.count())
                .isEqualTo(EXPECTED_COMMENT_LIST_SIZE);
    }

    @Test
    @DisplayName("должен вставлять комментарий в БД")
    void shouldInsertComment() {
        repository.save(new BookComment(FIFTH_COMMENT_ID, TEST_COMMENT_TEXT, em.find(Book.class, 1L)));
        em.clear();
        BookComment insertedComment = em.find(BookComment.class, FIFTH_COMMENT_ID);

        Assertions.assertThat(insertedComment)
                .usingRecursiveComparison()
                .isEqualTo(new BookComment(FIFTH_COMMENT_ID, TEST_COMMENT_TEXT, em.find(Book.class, 1L)));
    }

    @Test
    @DisplayName("должен возвращать ожидаемый комментарий по его ID")
    void shouldReturnExpectedCommentById() {
        BookComment bookCommentToAssert = em.find(BookComment.class, FIRST_COMMENT_ID);
        BookComment bookComment = repository.findById(FIRST_COMMENT_ID).get();
        Assertions.assertThat(bookComment)
                .usingRecursiveComparison()
                .isEqualTo(bookCommentToAssert);
    }

    @Test
    @DisplayName("должен возвращать список всех комментариев")
    void shouldReturnAllComments() {
        List<BookComment> commentList = repository.findAll();
        Assertions.assertThat(commentList)
                .hasSize(EXPECTED_ALL_COMMENTS_COUNT)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new BookComment(FIRST_COMMENT_ID, "comment 1", em.find(Book.class, 1L)),
                        new BookComment(SECOND_COMMENT_ID, "comment 2", em.find(Book.class, 2L)),
                        new BookComment(THIRD_COMMENT_ID, "comment 3", em.find(Book.class, 3L)),
                        new BookComment(FOURTH_COMMENT_ID, "comment 4", em.find(Book.class, 3L))
                );
    }

    @Test
    @DisplayName("должен возвращать комментарии к кинге по её id")
    void shouldReturnBookComments() {
        List<BookComment> bookComments = repository.findByBookId(3L);

        Assertions.assertThat(bookComments)
                .hasSize(2)
                .usingFieldByFieldElementComparator()
                .containsExactly(
                        new BookComment(THIRD_COMMENT_ID, "comment 3", em.find(Book.class, 3L)),
                        new BookComment(FOURTH_COMMENT_ID, "comment 4", em.find(Book.class, 3L))
                );
    }

    @Test
    @DisplayName("должен обновлять комментарий в БД")
    void shouldUpdateComment() {
        repository.updateById(THIRD_COMMENT_ID, "CommentUpdated");
        em.clear();
        BookComment bookComment = em.find(BookComment.class, THIRD_COMMENT_ID);
        Assertions.assertThat(bookComment)
                .usingRecursiveComparison()
                .isEqualTo(new BookComment(THIRD_COMMENT_ID, "CommentUpdated", em.find(Book.class, 3L)));

    }

    @Test
    @DisplayName("должен удалять комментарий из БД по его ID")
    void shouldDeleteCommentById() {
        BookComment deletingComment = em.find(BookComment.class, FIRST_COMMENT_ID);
        Assertions.assertThat(deletingComment).isNotNull();
        repository.deleteById(FIRST_COMMENT_ID);
        em.clear();
        Assertions.assertThat(repository.findById(FIRST_COMMENT_ID))
                .isEmpty();
    }
}