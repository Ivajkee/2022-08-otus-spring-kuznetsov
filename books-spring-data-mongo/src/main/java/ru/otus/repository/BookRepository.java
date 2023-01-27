package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);
}
