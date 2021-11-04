package ru.otus.library.services.ext;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

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

    private static final String BOOK_ID_PREFIX = "book-";
    private static final String AUTHOR_ID_PREFIX = "author-";
    private static final String GENRE_ID_PREFIX = "genre-";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookParamsProviderImpl(@Value("#{T (java.lang.System).in}") InputStream inputStream,
                                  @Value("#{T (java.lang.System).out}") PrintStream printStream,
                                  BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.inputStream = inputStream;
        this.printStream = printStream;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Author getAuthorFromUser() {
        lazyInputStreamInitialize();
        List<Author> authorList = authorRepository.findAll();
        int authorIdChosenByUser = -1;
        String authorNameEnteredByUser = null;
        for (int i = 0; i < authorList.size(); i++) {
            System.out.println((i + 1) + ". " + authorList.get(i).getName());
        }
        printStream.println("Введите порядковый номер автора книги, или введите имя нового");
        String userInput = "";
        try {
            userInput = reader.readLine();
            authorIdChosenByUser = Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException e){
            authorNameEnteredByUser = userInput;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Author resultAuthor;
        if (authorIdChosenByUser != -1) {
            resultAuthor = authorList.get(authorIdChosenByUser);
        } else {
            resultAuthor = new Author(AUTHOR_ID_PREFIX + String.format("%03d", authorList.size() + 1), authorNameEnteredByUser);
        }
        return resultAuthor;
    }

    @Override
    public List<Genre> getGenreListFromUser() {
        lazyInputStreamInitialize();
        List<Genre> genreList = genreRepository.findAll();
        List<Integer> genreIdsChosenByUser = new ArrayList<>();
        List<String> genreNamesChosenByUser = new ArrayList<>();
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println((i + 1) + ". " + genreList.get(i).getName());
        }
        printStream.println("Введите порядковый номер жанра книги, или введите имя нового");
        printStream.println("Можно ввести несколько значений через запятую");
        String tmpUserInputGenre = null;
        try {
            tmpUserInputGenre = reader.readLine();
            genreIdsChosenByUser = Arrays.stream(tmpUserInputGenre.split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            genreNamesChosenByUser = Arrays.stream(tmpUserInputGenre.split(",")).map(String::trim).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR while reading genre");
        }
        List<Genre> resultGenreList;
        if (genreIdsChosenByUser.size() > 0) {
            resultGenreList = genreIdsChosenByUser.stream().map(i -> genreList.get(i -1)).collect(Collectors.toList());
        } else {
            resultGenreList = new ArrayList<>();
            for (int i = 0; i < genreNamesChosenByUser.size(); i++) {
                resultGenreList.add(new Genre(GENRE_ID_PREFIX + String.format("%03d", genreList.size() + i + 1), genreNamesChosenByUser.get(i)));
            }
        }
        return resultGenreList;
    }

    @Override
    public List<Comment> getCommentsFromUser() {
        List<Comment> resultList = null;
        printStream.println("Введите комментарий к книге");
        printStream.println("Можно ввести несколько комментариев через запятую");
        String userInput;
        try {
            userInput = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] commentsTextList = null;
        if (userInput != null) {
            commentsTextList = userInput.split(",");
        }
        if (commentsTextList != null) {
            resultList = Arrays.stream(commentsTextList).map(s -> new Comment(ObjectId.get().toString(), s)).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public String getNewBookId() {
        return BOOK_ID_PREFIX + String.format("%03d", bookRepository.count() + 1);
    }

    private void lazyInputStreamInitialize() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(this.inputStream));
        }
    }
}
