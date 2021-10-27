package ru.otus.library.services.ext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("BookParamsProviderImpl должен")
class BookParamsProviderImplTest {

    @MockBean
    BookRepository bookRepository;

    private final List<Author> authorList = List.of(
            new Author("author-001", "Test Author 1"),
            new Author("author-002", "Test Author 2"),
            new Author("author-003", "Test Author 3")
    );

    private final List<Genre> genreList = List.of(
            new Genre("genre-001", "Test genre 1"),
            new Genre("genre-002", "Test genre 2"),
            new Genre("genre-003", "Test genre 3"),
            new Genre("genre-004", "Test genre 4")
    );

    @Test
    @DisplayName("запрашивать у пользователя выбрать автора из списка")
    void shouldAskUserForAuthor() {

        Mockito.when(bookRepository.findAllAuthors())
                .thenReturn(authorList);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("3\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProviderImpl bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream), bookRepository);

        Author returnedAuthor = bookParamsProvider.getAuthorFromUser();

        Assertions.assertThat(returnedAuthor)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(authorList.get(2));
    }

    @Test
    @DisplayName("возвращать нового автора имя которого пользователь ввел с клавиатуры")
    void shouldReturnAuthorEnteredByUser() {

        Mockito.when(bookRepository.findAllAuthors())
                .thenReturn(authorList);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("New Author\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProviderImpl bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream), bookRepository);

        Author returnedAuthor = bookParamsProvider.getAuthorFromUser();

        Assertions.assertThat(returnedAuthor)
                .isNotNull()
                .extracting(Author::getId).isEqualTo("author-004");

        Assertions.assertThat(returnedAuthor)
                .isNotNull()
                .extracting(Author::getName).isEqualTo("New Author");
    }

    @Test
    @DisplayName("запрашивать у пользователя выбрать жанр из списка")
    void shouldAskUserForChooseGenre() {

        Mockito.when(bookRepository.findAllGenres())
                .thenReturn(genreList);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("2,3\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProviderImpl bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream), bookRepository);

        List<Genre> returnedGenreList = bookParamsProvider.getGenreListFromUser();

        Assertions.assertThat(returnedGenreList)
                .isNotNull()
                .hasSize(2)
                .contains(genreList.get(1), genreList.get(2));
    }

    @Test
    @DisplayName("возвращать список новых жанров имена которых пользователь ввел с клавиатуры")
    void shouldReturnGenresEnteredByUser() {

        Mockito.when(bookRepository.findAllGenres())
                .thenReturn(genreList);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("New genre 1, New genre 2\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProviderImpl bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream), bookRepository);

        List<Genre> returnedGenreList = bookParamsProvider.getGenreListFromUser();
        System.out.println(returnedGenreList);

        Assertions.assertThat(returnedGenreList)
                .isNotNull()
                .hasSize(2)
                .contains(new Genre("genre-005", "New genre 1"), new Genre("genre-006", "New genre 2"));
    }

    @Test
    @DisplayName("генерировать и возвращать Id для новой книги")
    void shouldGenerateNewBookId() {
        Mockito.when(bookRepository.count())
                .thenReturn(10L);

        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProviderImpl bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream), bookRepository);

        Assertions.assertThat(bookParamsProvider.getNewBookId())
                .isEqualTo("book-011");
    }
}