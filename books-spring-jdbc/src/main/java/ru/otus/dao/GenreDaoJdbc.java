package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        Long count = jdbc.queryForObject("select count(*) from genres", Map.of(), Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public Genre save(Genre genre) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)", parameterSource, keyHolder);
        genre.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return genre;
    }

    @Override
    public Optional<Genre> findById(long id) {
        List<Genre> genres = jdbc.query("select id, name from genres where id = :id", Map.of("id", id), new GenreMapper());
        return genres.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select id, name from genres order by id", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    private static final class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Genre genre = new Genre();
            genre.setId(id);
            genre.setName(name);
            return genre;
        }
    }
}
