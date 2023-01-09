package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.otus.domain.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        Long count = jdbc.queryForObject("select count(*) from authors", Map.of(), Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public Author save(Author author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("fullName", author.getFullName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into authors (full_name) values (:fullName)", parameterSource, keyHolder);
        author.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return author;
    }

    @Override
    public Author update(Author author) {
        Map<String, Object> values = Map.of(
                "id", author.getId(),
                "fullName", author.getFullName()
        );
        jdbc.update("update authors set full_name = :fullName where id = :id", values);
        return author;
    }

    @Override
    public boolean existsById(long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject("select exists (select 1 from authors where id = :id)",
                Map.of("id", id), Boolean.class));
    }

    @Override
    public Optional<Author> findById(long id) {
        List<Author> authors = jdbc.query("select id, full_name from authors where id = :id",
                Map.of("id", id), new AuthorMapper());
        return CollectionUtils.isEmpty(authors) ? Optional.empty() : Optional.of(authors.get(0));
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, full_name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    private static final class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
