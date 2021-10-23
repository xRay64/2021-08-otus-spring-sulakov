package ru.otus.homeworklibrary.services.ext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.AuthorRepository;
import ru.otus.homeworklibrary.repositories.GenreRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("BookParamsProvider должен")
class BookParamsProviderImplTest {

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    GenreRepository genreRepository;

    @Test
    @DisplayName("возвращать автора выбранного пользователем")
    void shouldAscUserForAuthor() {
        Mockito.when(authorRepository.findAll())
                .thenReturn(List.of(new Author(1L, "Author1"), new Author(2L, "Author2")));
        Mockito.when(authorRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(new Author(1L, "Author1")));
        Mockito.when(authorRepository.findById(Mockito.eq(2L)))
                .thenReturn(Optional.of(new Author(2L, "Author2")));

        ByteArrayInputStream inputStream = new ByteArrayInputStream("2\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProvider bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream));

        Author returnedAuthor = bookParamsProvider.getAuthorFromUser(authorRepository);
        System.out.println(returnedAuthor);

        Assertions.assertThat(returnedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(new Author(2L, "Author2"));
    }

    @Test
    @DisplayName("возвращать список жанров выбранных пользователем")
    void shouldReturnGenreListSelectedByUser() {
        Mockito.when(genreRepository.findAll())
                .thenReturn(List.of(new Genre(1L, "Genre1"), new Genre(2L, "Genre2")));
        Mockito.when(genreRepository.findById(Mockito.eq(1L)))
                .thenReturn(Optional.of(new Genre(1L, "Genre1")));
        Mockito.when(genreRepository.findById(Mockito.eq(2L)))
                .thenReturn(Optional.of(new Genre(2L, "Genre2")));

        ByteArrayInputStream inputStream = new ByteArrayInputStream("1,2\n".getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BookParamsProvider bookParamsProvider = new BookParamsProviderImpl(inputStream, new PrintStream(outputStream));

        var returnedGenreList = bookParamsProvider.getGenreListFromUser(genreRepository);

        System.out.println(returnedGenreList);

        Assertions.assertThat(returnedGenreList)
                .hasSize(2)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new Genre(1L, "Genre1"),
                        new Genre(2L, "Genre2")
                );
    }
}