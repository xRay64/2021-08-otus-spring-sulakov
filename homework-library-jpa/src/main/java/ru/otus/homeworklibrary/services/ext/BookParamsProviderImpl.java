package ru.otus.homeworklibrary.services.ext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.AuthorRepository;
import ru.otus.homeworklibrary.repositories.GenreRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookParamsProviderImpl implements BookParamsProvider {
    private final InputStream inputStream;
    private final PrintStream printStream;
    private BufferedReader reader = null;

    public BookParamsProviderImpl(@Value("#{T (java.lang.System).in}") InputStream inputStream,
                                  @Value("#{T (java.lang.System).out}") PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    @Override
    public Author getAuthorFromUser(AuthorRepository repository) {
        lazyInputStreamInitialize();
        List<Author> authorList = repository.findAll();
        long authorIdChosenByUser = -1;
        String authorNameEnteredByUser = null;
        authorList.forEach(a -> printStream.println(a.getId() + ". " + a.getName()));
        printStream.println("Введите порядковый номер автора книги, или введите имя нового");
        String tmpUserInputAuthor = null;
        try {
            tmpUserInputAuthor = reader.readLine();
            authorIdChosenByUser = Long.parseLong(tmpUserInputAuthor);
        } catch (NumberFormatException e) {
            //пользователь ввел строку
            authorNameEnteredByUser = tmpUserInputAuthor;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR while reading author");
        }
        Author resultAuthor;
        if (authorIdChosenByUser != -1) {
            resultAuthor = repository.findById(authorIdChosenByUser).orElseThrow(() -> new RuntimeException("error while getting chosen author"));
        } else {
            resultAuthor = new Author(0, authorNameEnteredByUser);
        }
        return resultAuthor;
    }

    @Override
    public List<Genre> getGenreListFromUser(GenreRepository repository) {
        lazyInputStreamInitialize();
        List<Genre> genreList = repository.findAll();
        List<Long> genreIdsChosenByUser = new ArrayList<>();
        List<String> genreNamesChosenByUser = new ArrayList<>();
        genreList.forEach(g -> printStream.println(g.getId() + ". " + g.getName()));
        printStream.println("Введите порядковый номер жанра книги, или введите имя нового");
        printStream.println("Можно ввести несколько значений через запятую");
        String tmpUserInputGenre = null;
        try {
            tmpUserInputGenre = reader.readLine();
            genreIdsChosenByUser = Arrays.stream(tmpUserInputGenre.split(","))
                    .map(s -> Long.parseLong(s.trim()))
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            //Пользователь ввел текст
            genreNamesChosenByUser = Arrays.stream(tmpUserInputGenre.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR while reading genre");
        }

        List<Genre> resultGenreList;
        if (genreIdsChosenByUser.size() > 0) {
            List<Long> finalGenreIdsChosenByUser = genreIdsChosenByUser;
            resultGenreList = genreList.stream().filter(genre -> finalGenreIdsChosenByUser.contains(genre.getId())).collect(Collectors.toList());
        } else {
            resultGenreList = new ArrayList<>();
            for (String newGenreName : genreNamesChosenByUser) {
                resultGenreList.add(new Genre(0, newGenreName));
            }
        }
        return resultGenreList;
    }

    private void lazyInputStreamInitialize() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(this.inputStream));
        }
    }
}
