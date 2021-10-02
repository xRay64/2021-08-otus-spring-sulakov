package ru.otus.homeworklibrary.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.Genre;

import java.util.List;
import java.util.Optional;

@DisplayName("Repository для доступа к книгам должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final long FIRST_BOOK_ID = 1L;
    private static final long THIRD_BOOK_ID = 3L;
    private static final long FOURTH_BOOK_ID = 4L;

    private static final String THIRD_BOOK_NAME = "Book3";

    private static final int EXPECTED_BOOK_LIST_SIZE = 3;

    private static final int THIRD_BOOK_GENRE_LIST_SIZE = 2;

    public static final long FIRST_AUTHOR_ID = 1L;

    public static final String FIRST_AUTHOR_NAME = "Author1";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository repository;

    @DisplayName("возвращать определенное количество книг из БД")
    @Test
    void shouldReturnExpectedBooksCount() {
        Assertions.assertThat(repository.count())
                .isEqualTo(EXPECTED_BOOK_LIST_SIZE);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Optional<Book> book = repository.find(THIRD_BOOK_ID);

        Assertions.assertThat(book).isNotNull().get()
                .hasFieldOrPropertyWithValue("id", THIRD_BOOK_ID)
                .hasFieldOrPropertyWithValue("name", THIRD_BOOK_NAME);

        Assertions.assertThat(book.get().getAuthor())
                .first()
                .hasFieldOrPropertyWithValue("id", FIRST_AUTHOR_ID)
                .hasFieldOrPropertyWithValue("name", FIRST_AUTHOR_NAME);

        Assertions.assertThat(book.get().getGenreList())
                .hasSize(THIRD_BOOK_GENRE_LIST_SIZE)
                .extracting(Genre::getName)
                .containsExactlyInAnyOrder("Genre2", "Genre3");
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldReturnAllBooks() {
        List<Book> bookList = repository.findAll();

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
        Book expectedBook = em.find(Book.class, 1L);
        Assertions.assertThat(expectedBook).isNotNull();
        expectedBook.setId(4L);

        em.clear();

        repository.save(expectedBook);

        Book insertedBook = em.find(Book.class, FOURTH_BOOK_ID);

        System.out.println("expectedBook:\n" + expectedBook);
        System.out.println("insertedBook:\n" + insertedBook);

        Assertions.assertThat(insertedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

    }

    @DisplayName("обновлять книгу в базе")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = em.find(Book.class, THIRD_BOOK_ID);
        em.detach(expectedBook);
        expectedBook.setName("Book3Updated");
        expectedBook.setAuthor(List.of(new Author(6L, "Author10Inserted")));
        expectedBook.setGenreList(List.of(new Genre(1L, "Genre1")));
        repository.update(expectedBook);
        em.flush();
        em.clear();
        Book updatedBook = em.find(Book.class, THIRD_BOOK_ID);

        System.out.println(expectedBook);
        System.out.println(updatedBook);

        Assertions.assertThat(updatedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("удалять запись из базы используя id")
    @Test
    void shouldDeleteById() {
        repository.deleteById(FIRST_BOOK_ID);
        Assertions.assertThat(em.find(Book.class, FIRST_BOOK_ID))
                .isNull();
    }
}