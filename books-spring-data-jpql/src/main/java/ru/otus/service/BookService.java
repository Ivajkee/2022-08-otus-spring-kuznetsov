package ru.otus.service;

import ru.otus.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    long getCountOfBooks();

    BookDto saveBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    boolean existsBookById(long id);

    BookDto findBookById(long id);

    List<BookDto> findAllBooks();

    void deleteBookById(long id);

    void addAuthorToBook(long bookId, long authorId);

    void deleteAuthorFromBook(long bookId, long authorId);

    void addGenreToBook(long bookId, long genreId);

    void deleteGenreFromBook(long bookId, long genreId);
}
