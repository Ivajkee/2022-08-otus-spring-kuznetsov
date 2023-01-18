package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

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
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            Genre genre = em.createQuery("select g from Genre g where lower(g.name) = lower(:name)", Genre.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(genre);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g order by g.id", Genre.class).getResultList();
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
