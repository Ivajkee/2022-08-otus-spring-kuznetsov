package ru.otus.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select case when (count(*) > 0) then true else false end from Author where id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Optional<Author> findById(long id) {
        EntityGraph<?> authorBooksGraph = em.getEntityGraph("author-books-graph");
        Map<String, Object> properties = Map.of(FETCH.name(), authorBooksGraph);
        return Optional.ofNullable(em.find(Author.class, id, properties));
    }

    @Override
    public Optional<Author> findByFullName(String fullName) {
        EntityGraph<?> authorBooksGraph = em.getEntityGraph("author-books-graph");
        try {
            Author author = em.createQuery("select a from Author a where lower(a.fullName) = lower(:fullName)", Author.class)
                    .setParameter("fullName", fullName)
                    .setHint(FETCH.name(), authorBooksGraph)
                    .getSingleResult();
            return Optional.of(author);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        EntityGraph<?> authorBooksGraph = em.getEntityGraph("author-books-graph");
        return em.createQuery("select a from Author a", Author.class)
                .setHint(FETCH.name(), authorBooksGraph)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}
