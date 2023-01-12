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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public long getCountOfComments() {
        long count = commentRepository.count();
        log.debug("Found {} comments", count);
        return count;
    }

    @Transactional
    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = conversionService.convert(commentDto, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedCommentDto = conversionService.convert(savedComment, CommentDto.class);
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
    public boolean existsCommentById(long id) {
        boolean commentIsExist = commentRepository.existsById(id);
        log.debug("Comment with id {} is exist: {}", id, commentIsExist);
        return commentIsExist;
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
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentsDto = comments.stream()
                .map(comment -> conversionService.convert(comment, CommentDto.class))
                .collect(Collectors.toList());
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
