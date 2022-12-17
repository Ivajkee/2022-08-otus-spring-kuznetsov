package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        return Boolean.TRUE.equals(jdbc.queryForObject("select exists (select 1 from authors where id = :id)", Map.of("id", id), Boolean.class));
    }

    @Override
    public Optional<Author> findById(long id) {
        String sql = """
                select a.id, a.full_name, b.id as book_id, b.title as book_title, b.genre_id, g.id, g.name as genre_name from authors a
                left join books b on a.id = b.author_id
                left join genres g on b.genre_id = g.id where a.id = :id
                """;
        List<Author> authors = jdbc.query(sql, Map.of("id", id), new AuthorWithDetailExtractor());
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
            Author author = new Author();
            author.setId(id);
            author.setFullName(fullName);
            return author;
        }
    }

    private static final class AuthorWithDetailExtractor implements ResultSetExtractor<List<Author>> {
        @Override
        public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Author> authorMap = new HashMap<>();
            Author author;
            while (rs.next()) {
                long id = rs.getLong("id");
                author = authorMap.get(id);
                if (author == null) {
                    author = new Author();
                    author.setId(id);
                    author.setFullName(rs.getString("full_name"));
                    author.setBooks(new ArrayList<>());
                    authorMap.put(id, author);
                }
                long genreId = rs.getLong("genre_id");
                if (genreId > 0) {
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(rs.getString("genre_name"));
                    long bookId = rs.getLong("book_id");
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(rs.getString("book_title"));
                    book.setGenre(genre);
                    author.addBook(book);
                }
            }
            return new ArrayList<>(authorMap.values());
        }
    }
}
