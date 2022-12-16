package ru.otus.dao;

import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    long count();

    Genre save(Genre genre);

    Genre update(Genre genre);

    boolean existsById(long id);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    void deleteById(long id);
}
