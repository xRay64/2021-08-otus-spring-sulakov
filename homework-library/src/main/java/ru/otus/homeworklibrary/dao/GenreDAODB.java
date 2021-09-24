package ru.otus.homeworklibrary.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDAODB implements GenreDAO{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDAODB(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(*) from genres_tbl", Map.of(), Long.class);
    }

    @Override
    public Genre getById(long id) {
        return jdbcTemplate.queryForObject("select id, name from genres_tbl where id = :id",
                Map.of("id", id),
                (rs, rowNum) -> new Genre(rs.getLong("id"), rs.getString("name")));
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select id, name from genres_tbl",
                (rs, rowNum) -> new Genre(rs.getLong("id"), rs.getString("name")));
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into genres_tbl(name) values (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Genre genre) {
        jdbcTemplate.update("update genres_tbl set name = :name where id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void delete(Genre genre) {
        deleteById(genre.getId());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("delete from genres_tbl where id = :id", Map.of("id", id));
    }

    @Override
    public long getMaxId() {
        return jdbcTemplate.queryForObject("select max(id) from genres_tbl", Map.of(), Long.class);
    }

    /*private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }*/
}
