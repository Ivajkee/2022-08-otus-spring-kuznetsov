package ru.otus.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public boolean existsById(long id) {
        return em.createQuery("select case when (count(*) > 0) then true else false end from Genre where id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Optional<Genre> findById(long id) {
        EntityGraph<?> genreBooksGraph = em.getEntityGraph("genre-books-graph");
        Map<String, Object> properties = Map.of(FETCH.getKey(), genreBooksGraph);
        return Optional.ofNullable(em.find(Genre.class, id, properties));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        EntityGraph<?> genreBooksGraph = em.getEntityGraph("genre-books-graph");
        try {
            Genre genre = em.createQuery("select g from Genre g where lower(g.name) = lower(:name)", Genre.class)
                    .setParameter("name", name)
                    .setHint(FETCH.getKey(), genreBooksGraph)
                    .getSingleResult();
            return Optional.of(genre);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        EntityGraph<?> genreBooksGraph = em.getEntityGraph("genre-books-graph");
        return em.createQuery("select g from Genre g", Genre.class)
                .setHint(FETCH.getKey(), genreBooksGraph)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(this::delete);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }
}
