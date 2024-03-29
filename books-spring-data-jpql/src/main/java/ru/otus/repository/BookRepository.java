package ru.otus.repository;

import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book save(Book book);

    Book update(Book book);

    boolean existsById(long id);

    Optional<Book> findByIdWithInfo(long id);

    Optional<Book> findById(long id);

    Optional<Book> findByTitle(String title);

    List<Book> findAll();

    void deleteById(long id);
}
