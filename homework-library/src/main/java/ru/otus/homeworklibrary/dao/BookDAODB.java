package ru.otus.homeworklibrary.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Book;
import ru.otus.homeworklibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class BookDAODB implements BookDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from books_tbl", Map.of(), Long.class);
    }

    @Override
    public Book getById(long id) {
        return jdbcTemplate.queryForObject(
                "select b.id" +
                        " , b.name " +
                        " , a.id   as author_id " +
                        " , a.name as author_name " +
                        "from books_tbl b " +
                        "join authors_tbl a on b.author_id = a.id " +
                        "where b.id = :book_id ",
                Map.of("book_id", id),
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query(
                " select b.id" +
                        "  , b.name " +
                        "  , a.id   as author_id " +
                        "  , a.name as author_name " +
                        " from books_tbl b " +
                        " join authors_tbl a on b.author_id = a.id ",
                Map.of(),
                new BookMapper());
    }

    @Override
    public long add(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into books_tbl (id, name, author_id) values (:id, :name, :author_id)", params, kh);
        mergeIntoAuthors(book.getAuthor());
        mergeBookGenres(book.getGenreList());
        updateBookGenreRelation(book.getId(), book.getGenreList().stream().map(Genre::getId).collect(Collectors.toList()));
        return kh.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("update books_tbl " +
                        "           set name = :name " +
                        "             , author_id = :author_id " +
                        "         where id = :id "
                , Map.of("name", book.getName(), "author_id", book.getAuthor().getId(), "id", book.getId()));
        mergeIntoAuthors(book.getAuthor());
        mergeBookGenres(book.getGenreList());
        updateBookGenreRelation(book.getId(), book.getGenreList().stream().map(Genre::getId).collect(Collectors.toList()));
    }

    @Override
    public void delete(Book book) {
        deleteById(book.getId());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("delete from books_tbl where id = :id", Map.of("id", id));
    }

    @Override
    public long getMaxId() {
        return jdbcTemplate.queryForObject("select max(id) from books_tbl", Map.of(), Long.class);
    }

    private List<Genre> getGenreListByBookId(long book_id) {
        return jdbcTemplate.query(
                "select g.id, g.name" +
                        " from genres_tbl g" +
                        " join book_genre_tbl bg on g.id = bg.genre_id " +
                        "where bg.book_id = :book_id",
                Map.of("book_id", book_id),
                (rs, rowNum) -> new Genre(rs.getLong("id"), rs.getString("name")));
    }

    private void mergeBookGenres(List<Genre> genreList) {
        for (Genre genre : genreList) {
            jdbcTemplate.update(
                    "merge into genres_tbl key (id) values (:id, :name)",
                    Map.of("id", genre.getId(), "name", genre.getName()));
        }
    }

    private void mergeIntoAuthors(Author author) {
        jdbcTemplate.update("merge into authors_tbl key (id) values (:id, :name)",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    private void updateBookGenreRelation(long book_id, List<Long> genre_ids) {
        jdbcTemplate.update("delete from book_genre_tbl where book_id = :book_id"
                , Map.of("book_id", book_id));
        for (Long genre_id : genre_ids) {
            jdbcTemplate.update("insert into book_genre_tbl (book_id, genre_id) values (:book_id, :genre_id)"
                    , Map.of("book_id", book_id, "genre_id", genre_id));
        }
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("name"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name")),
                    getGenreListByBookId(rs.getLong("id")));
        }
    }
}
