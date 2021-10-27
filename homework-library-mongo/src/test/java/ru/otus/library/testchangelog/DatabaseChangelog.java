package ru.otus.library.testchangelog;

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
        authorMap.put("author-001", new Author("author-001", "Test author 1"));
        authorMap.put("author-002", new Author("author-002", "Test author 2"));
        authorMap.put("author-003", new Author("author-003", "Test author 3"));
        authorMap.put("author-004", new Author("author-004", "Test author 4"));

        Map<String, Genre> genreMap = new HashMap<>();
        genreMap.put("genre-001", new Genre("genre-001", "Test genre 1"));
        genreMap.put("genre-002", new Genre("genre-002", "Test genre 2"));
        genreMap.put("genre-003", new Genre("genre-003", "Test genre 3"));
        genreMap.put("genre-004", new Genre("genre-004", "Test genre 4"));

        bookList.add(new Book(
                "book-001",
                "Test book 1",
                List.of(authorMap.get("author-001")),
                List.of(genreMap.get("genre-001"))
        ));
        bookList.add(new Book(
                "book-002",
                "Test book 2",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-003",
                "Test book 3",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-004",
                "Test book 4",
                List.of(authorMap.get("author-002")),
                List.of(genreMap.get("genre-002"))
        ));
        bookList.add(new Book(
                "book-005",
                "Test book 5",
                List.of(authorMap.get("author-003"), authorMap.get("author-004")),
                List.of(genreMap.get("genre-003"), genreMap.get("genre-004"))
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
