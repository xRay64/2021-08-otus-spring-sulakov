package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.library.models.*;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.repositories.UserRepository;

import java.util.*;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private List<Book> bookList = new ArrayList<>();
    private Map<String, Author> authorMap = new HashMap<>();
    private Map<String, Genre> genreMap = new HashMap<>();


    @ChangeSet(order = "001", id = "dropDB", author = "Sulakov Anotn", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "Sulakov Anotn")
    public void insertAuthors(AuthorRepository repository) {
        authorMap.put("author-001", new Author("author-001", "Лев Николаевич Толстой"));
        authorMap.put("author-002", new Author("author-002", "Фёдор Михайлович Достоевский"));
        authorMap.put("author-003", new Author("author-003", "Илья Ильф"));
        authorMap.put("author-004", new Author("author-004", "Евгений Петров"));
        authorMap.put("author-005", new Author("author-005", "Самуил Яковлевич Маршак"));
        authorMap.put("author-006", new Author("author-006", "Александр Сергеевич Пушкин"));

        repository.saveAll(authorMap.values());
    }

    @ChangeSet(order = "003", id = "inserGenres", author = "SulakovAnton")
    public void insertGenres(GenreRepository repository) {
        genreMap.put("genre-001", new Genre("genre-001", "роман-эпопея"));
        genreMap.put("genre-002", new Genre("genre-002", "роман"));
        genreMap.put("genre-003", new Genre("genre-003", "художественный вымысел"));
        genreMap.put("genre-004", new Genre("genre-004", "литература о путешествиях"));
        genreMap.put("genre-005", new Genre("genre-005", "сатира"));
        genreMap.put("genre-006", new Genre("genre-006", "художественный вымысел"));
        genreMap.put("genre-007", new Genre("genre-007", "роман в стихах"));
        genreMap.put("genre-008", new Genre("genre-008", "драматическая сказка"));

        repository.saveAll(genreMap.values());
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "Sulakov Anotn")
    public void insertBooks(BookRepository repository) {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment("comment-001", "comment text 1 for book-001"));
        commentList.add(new Comment("comment-002", "comment text 2 for book-001"));
        commentList.add(new Comment("comment-003", "comment text 1 for book-003"));
        commentList.add(new Comment("comment-004", "comment text 1 for book-004"));
        commentList.add(new Comment("comment-005", "comment text 2 for book-004"));

        bookList.add(new Book(
                "book-001",
                "Война и Мир",
                List.of(authorMap.get("author-001")),
                List.of(genreMap.get("genre-001")),
                List.of(commentList.get(0), commentList.get(1))
        ));
        bookList.add(new Book(
                "book-002",
                "Преступление и наказание",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002")),
                null
        ));
        bookList.add(new Book(
                "book-003",
                "Братья Карамазовы",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002")),
                List.of(commentList.get(2))
        ));
        bookList.add(new Book(
                "book-004",
                "Идиот",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002")),
                List.of(commentList.get(3), commentList.get(4))
        ));
        bookList.add(new Book(
                "book-005",
                "Одноэтажная Америка",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-003"), genreMap.get("genre-004")),
                null
        ));
        bookList.add(new Book(
                "book-006",
                "12 стульев",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-002"), genreMap.get("genre-005"), genreMap.get("genre-006")),
                null
        ));
        bookList.add(new Book(
                "book-007",
                "Золотой теленок",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-002")),
                null
        ));
        bookList.add(new Book(
                "book-008",
                "Евгений Онегин",
                List.of(authorMap.get("author-006")),
                List.of(genreMap.get("genre-007")),
                null
        ));
        bookList.add(new Book(
                "book-009",
                "Двенадцать месяцев",
                List.of(authorMap.get("author-005")),
                List.of(genreMap.get("genre-008")),
                null
        ));

        repository.saveAll(bookList);
    }

    @ChangeSet(order = "005", id = "insertUsers", author = "Sulakov Anotn")
    public void insertUsers(UserRepository repository) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        repository.saveAll(
                List.of(
                        new User(UUID.randomUUID().toString(), "admin", bCryptPasswordEncoder.encode("admin"), List.of("ROLE_ADMIN"),true),
                        new User(UUID.randomUUID().toString(), "editor", bCryptPasswordEncoder.encode("password"), List.of("ROLE_EDITOR"), true),
                        new User(UUID.randomUUID().toString(), "user", bCryptPasswordEncoder.encode("123"), List.of("ROLE_USER"), true)
                )
        );
    }
}
