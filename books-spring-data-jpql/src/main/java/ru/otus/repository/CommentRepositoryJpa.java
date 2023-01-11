package ru.otus.repository;

import org.springframework.stereotype.Service;
import ru.otus.domain.model.Comment;

import java.util.List;
import java.util.Optional;

@Service
public class CommentRepositoryJpa implements CommentRepository {
    @Override
    public long count() {
        return 0;
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public Comment update(Comment comment) {
        return null;
    }

    @Override
    public boolean existsById(long id) {
        return false;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
