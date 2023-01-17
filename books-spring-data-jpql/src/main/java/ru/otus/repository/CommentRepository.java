package ru.otus.repository;

import ru.otus.domain.model.Comment;

import java.util.Optional;

public interface CommentRepository {
    long count();

    Comment update(Comment comment);

    Optional<Comment> findById(long id);

    void deleteById(long id);
}
