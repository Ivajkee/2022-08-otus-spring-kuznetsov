package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(String bookId, CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    CommentDto findCommentById(String id);

    List<CommentDto> findCommentsByBookId(String bookId);

    void deleteCommentById(String id);
}
