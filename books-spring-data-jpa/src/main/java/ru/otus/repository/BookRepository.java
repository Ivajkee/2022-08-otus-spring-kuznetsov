package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b left join fetch b.authors left join fetch b.genres where b.id = :id")
    Optional<Book> findByIdWithInfo(long id);

    @EntityGraph(attributePaths = {"authors", "genres"})
    Optional<Book> findByTitleIgnoreCase(String title);

    @Query("select b from Book b left join fetch b.authors left join fetch b.genres")
    List<Book> findAllWIthInfo();
}
