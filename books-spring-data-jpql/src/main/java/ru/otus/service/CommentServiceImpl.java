package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Comment;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.repository.CommentRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

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

    @Transactional
    @Override
    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
        log.debug("Comment with id {} deleted", id);
    }
}
