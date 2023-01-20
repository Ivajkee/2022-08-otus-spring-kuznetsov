package ru.otus.repository;

import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();

    Comment save(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByBook(Book book);

    void deleteById(long id);
}
