package ru.otus.library.repositories.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("CommentsCascadeDeleteEventListener дложен")
class CommentsCascadeDeleteEventListenerTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    public static final String FIRST_BOOK_ID = "book-001";
    public static final int EXPECTED_COMMENT_COUNT = 2;
    public static final int EXPECTED_COMMENT_COUNT_AFTER_DELETING = 0;

    @Test
    @DisplayName("удалять комментарии при удалении книги")
    void shouldDeleteCoommentsOnBookDeleting() {
        Book firstBook = bookRepository.findById(FIRST_BOOK_ID).get();

        List<Comment> commentListBeforeDeleting = commentRepository.getCommentByBook(firstBook);

        Assertions.assertThat(commentListBeforeDeleting)
                .hasSize(EXPECTED_COMMENT_COUNT);

        bookRepository.deleteById(FIRST_BOOK_ID);

        List<Comment> commentListAfterDeleting = commentRepository.getCommentByBook(firstBook);

        Assertions.assertThat(commentListAfterDeleting)
                .hasSize(EXPECTED_COMMENT_COUNT_AFTER_DELETING);

        //восстановим состояние БД
        commentRepository.saveAll(commentListBeforeDeleting);
    }
}