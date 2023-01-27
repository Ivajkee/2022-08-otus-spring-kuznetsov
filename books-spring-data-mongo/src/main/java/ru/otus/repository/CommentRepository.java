package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);
}
