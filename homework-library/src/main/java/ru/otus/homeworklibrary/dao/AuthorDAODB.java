package ru.otus.homeworklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDAODB implements AuthorDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDAODB(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from authors_tbl", Map.of(), Long.class);
    }

    @Override
    public Author getById(long id) {
        return jdbcTemplate.queryForObject("select id, name from authors_tbl where id = :id",
                Map.of("id", id),
                new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query("select id, name from authors_tbl", new AuthorMapper());
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into authors_tbl(name) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Author author) {
        jdbcTemplate.update("update authors_tbl set name = :name where id = :id",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void delete(Author author) {
        deleteById(author.getId());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("delete from authors_tbl where id = :id", Map.of("id", id));
    }

    @Override
    public long getMaxId() {
        return jdbcTemplate.queryForObject("select max(id) from authors_tbl", Map.of(), Long.class);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }
}
