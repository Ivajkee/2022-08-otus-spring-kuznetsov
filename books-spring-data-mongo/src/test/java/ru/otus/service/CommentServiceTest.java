package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Comment;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.sequence.SequenceGeneratorService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private SequenceGeneratorService sequenceGeneratorService;
    @MockBean
    private ConversionService conversionService;

    @DisplayName("Should add comment to book")
    @Test
    void shouldAddCommentToBook() {
        long bookId = 1;
        CommentDto commentDto = new CommentDto("Test comment");
        Book book = new Book(bookId, "Test book");
        Comment comment = new Comment(commentDto.getText(), book);
        Comment savedComment = new Comment(1, commentDto.getText(), book);
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(commentRepository.save(comment)).thenReturn(savedComment);
        when(conversionService.convert(savedComment, CommentDto.class)).thenReturn(expectedCommentDto);
        CommentDto actualCommentDto = commentService.saveComment(bookId, commentDto);
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Should throw exception when try add comment to not existing book")
    @Test
    void shouldThrowExceptionWhenTryAddCommentToNotExistingBook() {
        long bookId = 1;
        CommentDto commentDto = new CommentDto("Test comment");
        Book book = new Book(bookId, "Test book");
        Comment comment = new Comment(commentDto.getText(), book);
        Comment savedComment = new Comment(1, commentDto.getText(), book);
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(commentRepository.save(comment)).thenReturn(savedComment);
        when(conversionService.convert(savedComment, CommentDto.class)).thenReturn(expectedCommentDto);
        assertThatThrownBy(() -> commentService.saveComment(bookId, commentDto)).isInstanceOf(BookNotFoundException.class);
    }

    @DisplayName("Should update comment")
    @Test
    void shouldUpdateComment() {
        long id = 1;
        CommentDto commentDto = new CommentDto(id, "Test comment");
        Comment comment = new Comment(id, commentDto.getText());
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(expectedCommentDto);
        CommentDto actualCommentDto = commentService.updateComment(commentDto);
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Should throw exception when try update not existing comment")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingComment() {
        long id = 1;
        CommentDto commentDto = new CommentDto(id, "Test comment");
        Comment comment = new Comment(id, commentDto.getText());
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(expectedCommentDto);
        assertThatThrownBy(() -> commentService.updateComment(commentDto)).isInstanceOf(CommentNotFoundException.class);
    }

    @DisplayName("Should find comment by id")
    @Test
    void shouldFindCommentById() {
        long id = 1;
        Comment comment = new Comment(id, "Test comment");
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(expectedCommentDto);
        CommentDto actualCommentDto = commentService.findCommentById(id);
        assertThat(actualCommentDto).isEqualTo(expectedCommentDto);
    }

    @DisplayName("Should throw exception when try find not existing comment by id")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingCommentById() {
        long id = 1;
        Comment comment = new Comment(id, "Test comment");
        CommentDto expectedCommentDto = new CommentDto(comment.getId(), comment.getText());
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(expectedCommentDto);
        assertThatThrownBy(() -> commentService.findCommentById(id)).isInstanceOf(CommentNotFoundException.class);
    }

    @DisplayName("Should find comments by book id")
    @Test
    void shouldFindCommentsByBookId() {
        long bookId = 1;
        Book book = new Book(bookId, "Test book");
        Comment comment = new Comment(1, "Test comment");
        CommentDto commentDto = new CommentDto(comment.getId(), comment.getText());
        List<Comment> expectedComments = List.of(comment);
        List<CommentDto> expectedCommentsDto = List.of(commentDto);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(commentRepository.findAllByBook(book)).thenReturn(expectedComments);
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(commentDto);
        List<CommentDto> actualCommentsDto = commentService.findCommentsByBookId(bookId);
        assertThat(actualCommentsDto).isEqualTo(expectedCommentsDto);
    }

    @DisplayName("Should throw exception when try find comments by not existing book")
    @Test
    void shouldThrowExceptionWhenTryFindCommentsByNotExistingBook() {
        long bookId = 1;
        Book book = new Book(bookId, "Test book");
        Comment comment = new Comment(1, "Test comment");
        CommentDto commentDto = new CommentDto(comment.getId(), comment.getText());
        List<Comment> expectedComments = List.of(comment);
        List<CommentDto> expectedCommentsDto = List.of(commentDto);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(commentRepository.findAllByBook(book)).thenReturn(expectedComments);
        when(conversionService.convert(comment, CommentDto.class)).thenReturn(commentDto);
        List<CommentDto> actualCommentsDto = commentService.findCommentsByBookId(bookId);
        assertThat(actualCommentsDto).isEqualTo(expectedCommentsDto);
    }

    @DisplayName("Should delete comment")
    @Test
    void shouldDeleteComment() {
        long id = 1;
        assertThatCode(() -> commentService.deleteCommentById(id)).doesNotThrowAnyException();
    }
}