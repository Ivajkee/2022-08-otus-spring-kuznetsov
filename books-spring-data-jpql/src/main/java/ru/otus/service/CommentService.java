package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

import java.util.List;

public interface CommentService {
    long getCountOfComments();

    CommentDto saveComment(CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    boolean existsCommentById(long id);

    CommentDto findCommentById(long id);

    List<CommentDto> findAllComments();

    void deleteCommentById(long id);
}
