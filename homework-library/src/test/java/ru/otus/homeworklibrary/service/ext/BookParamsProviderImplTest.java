package ru.otus.homeworklibrary.service.ext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.dao.GenreDAO;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Genre;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("BookParamsProvider должен")
class BookParamsProviderImplTest {

    @MockBean
    AuthorDAO authorDAO;

    @MockBean
    GenreDAO genreDAO;

    @Test
    @DisplayName("возвращать автора выбранного пользователем")
    void shouldAscUserForAuthor() {
        Mockito.when(authorDAO.getAll())
                .thenReturn(List.of(new Author(1L, "Author1"), new Author(2L, "Author2")));

        ByteArrayInputStream inputStream = new ByteArrayInputStream("2\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProvider bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream));

        Author returnedAuthor = bookParamsProvider.getAuthorFromUser(authorDAO);
        System.out.println(returnedAuthor);

        Assertions.assertThat(returnedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(new Author(2L, "Author2"));
    }

    @Test
    @DisplayName("возвращать список жанров выбранных пользователем")
    void shouldReturnGenreListSelectedByUser() {
        Mockito.when(genreDAO.getAll())
                .thenReturn(List.of(new Genre(1L, "Genre1"), new Genre(2L, "Genre2")));

        ByteArrayInputStream inputStream = new ByteArrayInputStream("1,2\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProvider bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream));

        var returnedGenreList = bookParamsProvider.getGenreListFromUser(genreDAO);

        System.out.println(returnedGenreList);

        Assertions.assertThat(returnedGenreList)
                .hasSize(2);
        Assertions.assertThat(returnedGenreList)
                .first()
                .usingRecursiveComparison()
                .isEqualTo(new Genre(1L, "Genre1"));
        Assertions.assertThat(returnedGenreList)
                .last()
                .usingRecursiveComparison()
                .isEqualTo(new Genre(2L, "Genre2"));
    }
}