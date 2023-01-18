package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(long bookId, CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    CommentDto findCommentById(long id);

    List<CommentDto> findCommentsByBookId(long bookId);

    void deleteCommentById(long id);
}
