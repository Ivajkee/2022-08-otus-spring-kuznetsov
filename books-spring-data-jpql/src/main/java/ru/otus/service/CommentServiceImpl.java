package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Override
    public long getCountOfComments() {
        return 0;
    }

    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        return null;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        return null;
    }

    @Override
    public boolean existsCommentById(long id) {
        return false;
    }

    @Override
    public CommentDto findCommentById(long id) {
        return null;
    }

    @Override
    public List<CommentDto> findAllComments() {
        return null;
    }

    @Override
    public void deleteCommentById(long id) {

    }
}
