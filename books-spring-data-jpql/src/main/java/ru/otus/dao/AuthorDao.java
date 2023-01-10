package ru.otus.dao;

import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    long count();

    Author save(Author author);

    Author update(Author author);

    boolean existsById(long id);

    Optional<Author> findById(long id);

    List<Author> findAll();

    void deleteById(long id);
}
