package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(c) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBook(Book book) {
        return em.createQuery("select c from Comment c where c.book = :book", Comment.class)
                .setParameter("book", book)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(em::remove);
    }
}
