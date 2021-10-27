package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private List<Book> bookList = new ArrayList<>();

    @ChangeSet(order = "001", id = "dropDB", author = "Sulakov Anotn", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "Sulakov Anotn")
    public void insertBooks(BookRepository repository) {
        Map<String, Author> authorMap = new HashMap<>();
        authorMap.put("author-001", new Author("author-001", "Лев Николаевич Толстой"));
        authorMap.put("author-002", new Author("author-002", "Фёдор Михайлович Достоевский"));
        authorMap.put("author-003", new Author("author-003", "Илья Ильф"));
        authorMap.put("author-004", new Author("author-004", "Евгений Петров"));
        authorMap.put("author-005", new Author("author-005", "Самуил Яковлевич Маршак"));
        authorMap.put("author-006", new Author("author-006", "Александр Сергеевич Пушкин"));

        Map<String, Genre> genreMap = new HashMap<>();
        genreMap.put("genre-001", new Genre("genre-001", "роман-эпопея"));
        genreMap.put("genre-002", new Genre("genre-002", "роман"));
        genreMap.put("genre-003", new Genre("genre-003", "художественный вымысел"));
        genreMap.put("genre-004", new Genre("genre-004", "литература о путешествиях"));
        genreMap.put("genre-005", new Genre("genre-005", "сатира"));
        genreMap.put("genre-006", new Genre("genre-006", "художественный вымысел"));
        genreMap.put("genre-007", new Genre("genre-007", "роман в стихах"));
        genreMap.put("genre-008", new Genre("genre-008", "драматическая сказка"));

        bookList.add(new Book(
                "book-001",
                "Война и Мир",
                List.of(authorMap.get("author-001")),
                List.of(genreMap.get("genre-001"))
        ));
        bookList.add(new Book(
                "book-002",
                "Преступление и наказание",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-003",
                "Братья Карамазовы",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-004",
                "Идиот",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-005",
                "Одноэтажная Америка",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-003"), genreMap.get("genre-004"))
        ));
        bookList.add(new Book(
                "book-006",
                "12 стульев",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-002"), genreMap.get("genre-005"), genreMap.get("genre-006"))
        ));
        bookList.add(new Book(
                "book-007",
                "Золотой теленок",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-008",
                "Евгений Онегин",
                List.of(authorMap.get("author-006")),
                List.of(genreMap.get("genre-007"))
        ));
        bookList.add(new Book(
                "book-009",
                "Двенадцать месяцев",
                List.of(authorMap.get("author-005")),
                List.of(genreMap.get("genre-008"))
        ));

        repository.saveAll(bookList);
    }

    @ChangeSet(order = "003", id = "insertComments", author = "Sulakov Anton")
    public void insertComments(CommentRepository repository) {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment("comment-001", "comment text 1 for book-001", bookList.get(0)));
        commentList.add(new Comment("comment-002", "comment text 2 for book-001", bookList.get(0)));
        commentList.add(new Comment("comment-003", "comment text 1 for book-003", bookList.get(2)));
        commentList.add(new Comment("comment-004", "comment text 1 for book-004", bookList.get(3)));
        commentList.add(new Comment("comment-005", "comment text 2 for book-004", bookList.get(3)));

        repository.saveAll(commentList);
    }
}
