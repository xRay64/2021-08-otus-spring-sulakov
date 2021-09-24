package ru.otus.homeworklibrary.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Book;
import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

@JdbcTest
@DisplayName("DAO для доступа к книгам должен")
@Import(BookDAODB.class)
class BookDAODBTest {
    private static final int EXPECTED_BOOK_LIST_SIZE = 3;
    @Autowired
    private BookDAO bookDAO;

    @DisplayName("возвращать определенное количество книг из БД")
    @Test
    void shouldReturnExpectedBooksCount() {
        Assertions.assertThat(bookDAO.count())
                .isEqualTo(3);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(3L,
                "Book3",
                new Author(2L, "Author2"),
                List.of(new Genre(2L, "Genre2"), new Genre(3L, "Genre3")));
        Book book = bookDAO.getById(3L);

        Assertions.assertThat(book)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = bookDAO.getAll();

        Assertions.assertThat(bookList)
                .hasSize(EXPECTED_BOOK_LIST_SIZE)
                .allMatch(book -> !"".equals(book.getName()))
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenreList().size() >= 1);
        bookList.forEach(System.out::println);
    }

    @DisplayName("добавлять книгу в базу")
    @Test
    void shouldAddBook() {
        Book expectedBook = new Book(
                4L,
                "Book4",
                new Author(3L, "Author3Updated"),
                List.of(new Genre(3L, "Genre3Updated"), new Genre(4L, "Genre4Inserted"))
        );
        bookDAO.add(expectedBook);
        Book insertedBook = bookDAO.getById(4L);

        Assertions.assertThat(insertedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

        System.out.println("expectedBook:\n" + expectedBook);
        System.out.println("insertedBook:\n" + insertedBook);
    }

    @DisplayName("обновлять книгу в базе")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = bookDAO.getById(3L);
        expectedBook.setName("Book3Updated");
        expectedBook.setAuthor(new Author(10L, "Author10Inserted"));
        expectedBook.setGenreList(List.of(new Genre(1L, "Genre1")));

        bookDAO.update(expectedBook);

        Book updatedBook = bookDAO.getById(3L);

        Assertions.assertThat(updatedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("удалять запись из базы используя объект")
    @Test
    void shouldDeleteByObject() {
        Book bookForDelete = bookDAO.getById(1L);
        bookDAO.delete(bookForDelete);

        Assertions.assertThatCode(() -> bookDAO.getById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("удалять запись из базы используя id")
    @Test
    void shouldDeleteById() {
        bookDAO.deleteById(2L);

        Assertions.assertThatCode(() -> bookDAO.getById(2L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("возвращать корректный максимальный id")
    void shouldReturnCorrectMaxID() {
        Assertions.assertThat(bookDAO.getMaxId())
                .isEqualTo(3);
    }
}