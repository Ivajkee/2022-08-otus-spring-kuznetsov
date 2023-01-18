package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Comment;
import ru.otus.exception.BookNotFoundException;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final ConversionService conversionService;

    @Transactional
    @Override
    public CommentDto saveComment(long bookId, CommentDto commentDto) {
        CommentDto savedCommentDto = bookRepository.findById(bookId).map(book -> {
            Comment comment = new Comment(commentDto.getText(), book);
            Comment savedComment = commentRepository.save(comment);
            return conversionService.convert(savedComment, CommentDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Saved comment: {}", savedCommentDto);
        return savedCommentDto;
    }

    @Transactional
    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        CommentDto updatedCommentDto = commentRepository.findById(commentDto.getId()).map(comment -> {
            comment.setText(commentDto.getText());
            return conversionService.convert(comment, CommentDto.class);
        }).orElseThrow(() -> new CommentNotFoundException(commentDto.getId()));
        log.debug("Updated comment: {}", updatedCommentDto);
        return updatedCommentDto;
    }

    @Override
    public CommentDto findCommentById(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        CommentDto commentDto = conversionService.convert(comment, CommentDto.class);
        log.debug("Found comment: {}", commentDto);
        return commentDto;
    }

    @Override
    public List<CommentDto> findCommentsByBookId(long bookId) {
        List<CommentDto> commentsDto = bookRepository.findById(bookId)
                .map(book -> commentRepository.findAllByBook(book)
                        .stream()
                        .map(comment -> conversionService.convert(comment, CommentDto.class))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new BookNotFoundException(bookId));
        log.debug("Found comments: {}", commentsDto);
        return commentsDto;
    }

    @Transactional
    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
        log.debug("Comment with id {} deleted", id);
    }
}
