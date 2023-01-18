package ru.otus.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select case when (count(*) > 0) then true else false end from Book where id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> bookGraph = em.getEntityGraph("book-graph");
        Map<String, Object> properties = Map.of(FETCH.getKey(), bookGraph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        EntityGraph<?> bookGraph = em.getEntityGraph("book-graph");
        try {
            Book book = em.createQuery("select b from Book b where lower(b.title) = lower(:title)", Book.class)
                    .setParameter("title", title)
                    .setHint(FETCH.getKey(), bookGraph)
                    .getSingleResult();
            return Optional.of(book);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> bookGraph = em.getEntityGraph("book-graph");
        return em.createQuery("select b from Book b", Book.class)
                .setHint(FETCH.getKey(), bookGraph)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(em::remove);
    }
}
