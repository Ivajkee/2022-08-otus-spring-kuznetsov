package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "genres"})
    Optional<Book> findById(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "genres"})
    Optional<Book> findByTitleIgnoreCase(String title);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"authors", "genres"})
    List<Book> findAll();
}
