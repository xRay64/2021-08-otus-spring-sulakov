package ru.otus.homeworklibrary.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.services.ext.BookParamsProvider;
import ru.otus.homeworklibrary.services.ext.BookParamsProviderImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
        , ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        ,
})
@DisplayName("BookService должен")
class BookServiceImplTest {
    private final static String INPUT_SREAM_STRING ="1\n1,2\n1\n1,2\n";
    private static ByteArrayInputStream inputStream = new ByteArrayInputStream(INPUT_SREAM_STRING.getBytes());
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @TestConfiguration
    static class BootTestConfig {
        @Bean
        BookParamsProvider bookParamsProvider() {
            return new BookParamsProviderImpl(inputStream, new PrintStream(outputStream));
        }
    }

    @Autowired
    BookService bookService;

    @Test
    @DisplayName("дложен возвращать список всех книг")
    void shouldReturnAllBooks() {
        var listOfBooks = bookService.getAll();
        Assertions.assertThat(listOfBooks)
                .isNotNull()
                .hasSize(3)
                .allMatch(book -> !"".equals(book.getName()))
                .allMatch(book -> book.getName().startsWith("Book"))
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenreList() != null);
    }

    @Test
    @DisplayName("возвращать книгу по ее ID")
    void shouldReturnBookByID() {
        Book returnedBook = bookService.get(1L);
        System.out.println(returnedBook);

        Assertions.assertThat(returnedBook)
                .isNotNull()
                .extracting(Book::getName)
                .isEqualTo("Book1");

        Assertions.assertThat(returnedBook)
                .extracting(Book::getId)
                .isEqualTo(1L);

        Assertions.assertThat(returnedBook)
                .extracting(Book::getAuthor)
                .extracting(authors -> authors.get(0).getName())
                .isEqualTo("Author2");

        Assertions.assertThat(returnedBook.getGenreList())
                .hasSize(1)
                .containsExactly(new Genre(1L, "Genre1"));
    }

    @Test
    @DisplayName("должен вставлять книгу в БД")
    void shouldInsertBook() {
        long newBookId = bookService.add("insertingBook");
        Book insertedBook = bookService.get(newBookId);
        System.out.println(insertedBook);

        Assertions.assertThat(insertedBook)
                .isNotNull()
                .matches(book -> "insertingBook".equals(book.getName()))
                .matches(book -> book.getId() == newBookId)
                .matches(book -> "Author1".equals(book.getAuthor().get(0).getName()))
                .matches(book -> book.getGenreList().size() == 2);
    }

    @Test
    @DisplayName("должен обновлять книгу в БД")
    void shouldUpdateBook() {
        bookService.update(3L, "Book3Updated");
        Book updatedBook = bookService.get(3L);
        Assertions.assertThat(updatedBook)
                .isNotNull()
                .matches(book -> "Book3Updated".equals(book.getName()))
                .matches(book -> book.getId() == 3L)
                .matches(book -> "Author1".equals(book.getAuthor().get(0).getName()))
                .matches(book -> book.getGenreList().size() == 2);
    }

    @Test
    @DisplayName("должен удалять книгу из БД")
    void shouldDeleteBook() {
        int bookListSize = bookService.getAll().size();
        bookService.delete(1L);
        Assertions.assertThat(bookService.getAll().size())
                .isEqualTo(bookListSize - 1);
    }
}