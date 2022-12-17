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
    public Genre update(Genre genre) {
        Map<String, Object> values = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
        jdbc.update("update genres set name = :name where id = :id", values);
        return genre;
    }

    @Override
    public boolean existsById(long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject("select exists (select 1 from genres where id = :id)", Map.of("id", id), Boolean.class));
    }

    @Override
    public Optional<Genre> findById(long id) {
        String sql = """
                select g.id, g.name, b.id as book_id, b.title as book_title, b.author_id, a.id, a.full_name as author_name from genres g
                left join books b on g.id = b.genre_id
                left join authors a on b.author_id = a.id where g.id = :id
                """;
        List<Genre> genres = jdbc.query(sql, Map.of("id", id), new GenreWithDetailExtractor());
        return CollectionUtils.isEmpty(genres) ? Optional.empty() : Optional.of(genres.get(0));
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

    private static final class GenreWithDetailExtractor implements ResultSetExtractor<List<Genre>> {
        @Override
        public List<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Genre> genreMap = new HashMap<>();
            Genre genre;
            while (rs.next()) {
                long id = rs.getLong("id");
                genre = genreMap.get(id);
                if (genre == null) {
                    genre = new Genre();
                    genre.setId(id);
                    genre.setName(rs.getString("name"));
                    genre.setBooks(new ArrayList<>());
                    genreMap.put(id, genre);
                }
                long authorId = rs.getLong("author_id");
                if (authorId > 0) {
                    Author author = new Author();
                    author.setId(authorId);
                    author.setFullName(rs.getString("author_name"));
                    long bookId = rs.getLong("book_id");
                    Book book = new Book();
                    book.setId(bookId);
                    book.setTitle(rs.getString("book_title"));
                    book.setAuthor(author);
                    genre.addBook(book);
                }
            }
            return new ArrayList<>(genreMap.values());
        }
    }
}
