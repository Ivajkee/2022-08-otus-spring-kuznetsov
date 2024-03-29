package ru.otus.repository;

import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();

    Genre save(Genre genre);

    Genre update(Genre genre);

    boolean existsById(long id);

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    void deleteById(long id);

    void delete(Genre genre);
}
