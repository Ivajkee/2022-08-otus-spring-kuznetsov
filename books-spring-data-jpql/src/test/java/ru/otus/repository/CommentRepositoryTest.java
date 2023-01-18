package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(CommentRepositoryJpa.class)
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should return expected comments count")
    @Test
    void shouldReturnExpectedCommentsCount() {
        long actualCount = commentRepository.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save comment")
    @Test
    void shouldSaveComment() {
        Book book = new Book("Test book");
        Book savedBook = em.persistAndFlush(book);
        Comment comment = new Comment("Test comment", savedBook);
        Comment savedComment = commentRepository.save(comment);
        Optional<Comment> optionalActualComment = commentRepository.findById(savedComment.getId());
        assertThat(optionalActualComment).get().extracting(Comment::getText).isEqualTo(comment.getText());
    }

    @DisplayName("Should update comment")
    @Test
    void shouldUpdateComment() {
        Comment comment = new Comment(3, "Edited comment");
        Comment updatedComment = commentRepository.update(comment);
        Optional<Comment> optionalActualComment = commentRepository.findById(updatedComment.getId());
        assertThat(optionalActualComment).get().extracting(Comment::getText).isEqualTo(updatedComment.getText());
    }

    @DisplayName("Should find comment by id")
    @Test
    void shouldFindCommentById() {
        Comment expectedComment = new Comment(1, "comment 1");
        Optional<Comment> optionalActualComment = commentRepository.findById(1);
        assertThat(optionalActualComment).hasValue(expectedComment);
    }

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

    @DisplayName("Should delete comment")
    @Test
    void shouldDeleteComment() {
        Comment expectedComment = new Comment(1, "comment 1");
        Optional<Comment> optionalComment = commentRepository.findById(expectedComment.getId());
        assertThat(optionalComment).hasValue(expectedComment);
        commentRepository.deleteById(expectedComment.getId());
        Optional<Comment> optionalDeletedComment = commentRepository.findById(expectedComment.getId());
        assertThat(optionalDeletedComment).isEmpty();
    }
}