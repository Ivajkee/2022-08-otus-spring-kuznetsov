package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

public interface CommentService {
    CommentDto updateComment(CommentDto commentDto);

    CommentDto findCommentById(long id);

    void deleteCommentById(long id);
}
