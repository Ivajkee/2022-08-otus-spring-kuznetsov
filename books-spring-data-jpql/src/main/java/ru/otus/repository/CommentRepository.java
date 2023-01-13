package ru.otus.repository;

import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();

    Comment update(Comment comment);

    boolean existsById(long id);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    List<Comment> findAllByBook(Book book);

    void deleteById(long id);
}
