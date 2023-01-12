package ru.otus.service;

import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.CommentDto;

import java.util.List;

public interface BookService {
    long getCountOfBooks();

    BookDto saveBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    boolean existsBookById(long id);

    BookDto findBookById(long id);

    List<BookDto> findAllBooks();

    void deleteBookById(long id);

    BookDto addAuthorToBook(long bookId, long authorId);

    BookDto deleteAuthorFromBook(long bookId, long authorId);

    BookDto addGenreToBook(long bookId, long genreId);

    BookDto deleteGenreFromBook(long bookId, long genreId);

    BookDto addCommentToBook(long bookId, CommentDto commentDto);

    BookDto deleteCommentFromBook(long bookId, long commentId);
}
