package ru.otus.homeworklibrary.service.ext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.dao.GenreDAO;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Genre;

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
    public Author getAuthorFromUser(AuthorDAO dao) {
        lazyInputStreamInitialize();
        List<Author> authorList = dao.getAll();
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
        Author resultAuthor = null;
        if (authorIdChosenByUser != -1) {
            var authorsMap = authorList.stream().collect(Collectors.toMap(Author::getId, Author::getName));
            resultAuthor = new Author(authorIdChosenByUser, authorsMap.get(authorIdChosenByUser));
        } else {
            resultAuthor = new Author(dao.getMaxId() + 1, authorNameEnteredByUser);
        }
        return resultAuthor;
    }

    @Override
    public List<Genre> getGenreListFromUser(GenreDAO dao) {
        lazyInputStreamInitialize();
        List<Genre> genreList = dao.getAll();
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

        List<Genre> resultGenreList = null;
        if (genreIdsChosenByUser.size() > 0) {
            List<Long> finalGenreIdsChosenByUser = genreIdsChosenByUser;
            resultGenreList = genreList.stream().filter(genre -> finalGenreIdsChosenByUser.contains(genre.getId())).collect(Collectors.toList());
        } else {
            resultGenreList = new ArrayList<>();
            long newId = dao.getMaxId() + 1;
            for (String newGenreName : genreNamesChosenByUser) {
                resultGenreList.add(new Genre(newId, newGenreName));
                newId++;
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
