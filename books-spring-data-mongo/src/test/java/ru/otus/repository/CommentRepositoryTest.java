package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.config.MongoDataInitializer;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.service.sequence.SequenceGeneratorService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoDataInitializer.class, SequenceGeneratorService.class})
@DataMongoTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Should find all comments by book")
    @Test
    void shouldFindAllCommentsByBook() {
        Book expectedBook = new Book(1, "Руслан и Людмила");
        Comment expectedComment = new Comment(1, "comment 1", expectedBook);
        List<Comment> expectedComments = List.of(expectedComment);
        List<Comment> actualComments = commentRepository.findAllByBook(expectedBook);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @DisplayName("Should delete all comments by book")
    @Test
    void shouldDeleteAllCommentsByBook() {
        Book book = new Book(1, "Руслан и Людмила");
        commentRepository.deleteAllByBook(book);
        List<Comment> actualComments = commentRepository.findAllByBook(book);
        assertThat(actualComments).isEmpty();
    }
}