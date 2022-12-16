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
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long count() {
        Long count = jdbc.queryForObject("select count(*) from books", Map.of(), Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book save(Book book) {
        Map<String, Object> values = new HashMap<>();
        values.put("title", book.getTitle());
        values.put("authorId", book.getAuthor().getId());
        values.put("genreId", book.getGenre().getId());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into books (title, author_id, genre_id) values (:title, :authorId, :genreId)", parameterSource, keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return book;
    }

    @Override
    public Book update(Book book) {
        Map<String, Object> values = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()
        );
        jdbc.update("update books set title = :title, author_id = :authorId, genre_id = :genreId where id = :id", values);
        return book;
    }

    @Override
    public boolean existsById(long id) {
        return Boolean.TRUE.equals(jdbc.queryForObject("select exists (select 1 from books where id = :id)", Map.of("id", id), Boolean.class));
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = """
                select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name from books b
                left join authors a on b.author_id = a.id
                left join genres g on b.genre_id = g.id where b.id = :id
                """;
        List<Book> books = jdbc.query(sql, Map.of("id", id), new BookWithDetailExtractor());
        return CollectionUtils.isEmpty(books) ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select id, title from books", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private static final class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            return book;
        }
    }

    private static final class BookWithDetailExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Book> bookMap = new HashMap<>();
            Book book;
            while (rs.next()) {
                long id = rs.getLong("id");
                book = bookMap.get(id);
                if (book == null) {
                    book = new Book();
                    book.setId(id);
                    book.setTitle(rs.getString("title"));
                    bookMap.put(id, book);
                }
                long authorId = rs.getLong("author_id");
                Author author = new Author();
                author.setId(authorId);
                author.setName(rs.getString("author_name"));
                book.setAuthor(author);
                long genreId = rs.getLong("genre_id");
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(rs.getString("genre_name"));
                book.setGenre(genre);
            }
            return new ArrayList<>(bookMap.values());
        }
    }
}
