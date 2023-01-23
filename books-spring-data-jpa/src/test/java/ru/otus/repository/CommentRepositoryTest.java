package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should find all comments by book")
    @Test
    void shouldFindAllCommentsByBook() {
        Book book = new Book("Test book");
        Book savedBook = em.persist(book);
        Comment comment = new Comment("Test comment", savedBook);
        Comment savedComment = em.persistAndFlush(comment);
        List<Comment> expectedComments = List.of(savedComment);
        List<Comment> actualComments = commentRepository.findAllByBook(book);
        assertThat(actualComments).isEqualTo(expectedComments);
    }
}