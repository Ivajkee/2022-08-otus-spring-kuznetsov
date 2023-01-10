package ru.otus.dao;

import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long count();

    Book save(Book book);

    Book update(Book book);

    boolean existsById(long id);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void deleteById(long id);
}
