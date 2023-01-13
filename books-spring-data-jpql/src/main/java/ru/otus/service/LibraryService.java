package ru.otus.service;

import ru.otus.domain.dto.CommentDto;

public interface LibraryService {
    void addAuthorToBook(long authorId, long bookId);

    void deleteAuthorFromBook(long authorId, long bookId);

    void addGenreToBook(long genreId, long bookId);

    void deleteGenreFromBook(long genreId, long bookId);

    void addCommentToBook(long bookId, CommentDto commentDto);
}
