package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)", parameterSource, keyHolder);
        author.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return author;
    }

    @Override
    public Optional<Author> findById(long id) {
        List<Author> authors = jdbc.query("select id, name from authors where id = :id", Map.of("id", id), new AuthorMapper());
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, name from authors", new AuthorMapper());
//        return jdbc.query("select a.id, a.name, b.id as book_id, b.title as book_title from authors a left join books b on a.id = b.author_id",
//                new AuthorWithDetailExtractor());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    private static final class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author();
            author.setId(id);
            author.setName(name);
            return author;
        }
    }
}
