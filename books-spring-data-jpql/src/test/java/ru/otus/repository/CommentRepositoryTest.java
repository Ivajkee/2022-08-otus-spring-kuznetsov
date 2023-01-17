package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(CommentRepositoryJpa.class)
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("Should return expected comments count")
    @Test
    void shouldReturnExpectedCommentsCount() {
        long actualCount = commentRepository.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should update comment")
    @Test
    void shouldUpdateComment() {
        Comment expectedComment = new Comment(3, "Edited comment");
        expectedComment = commentRepository.update(expectedComment);
        Optional<Comment> optionalActualComment = commentRepository.findById(expectedComment.getId());
        assertThat(optionalActualComment).get().extracting(Comment::getText).isEqualTo(expectedComment.getText());
    }

    @DisplayName("Should find comment by id")
    @Test
    void shouldFindCommentById() {
        Comment expectedComment = new Comment(1, "comment 1");
        Optional<Comment> optionalActualComment = commentRepository.findById(1);
        assertThat(optionalActualComment).hasValue(expectedComment);
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