package ru.otus.service;

import ru.otus.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    long getCountOfBooks();

    BookDto saveBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    boolean existsBookById(long id);

    BookDto findBookById(long id);

    BookDto findBookByTitle(String title);

    List<BookDto> findAllBooks();

    List<BookDto> findAllBooksByAuthor(long authorId);

    List<BookDto> findAllBooksByGenre(long genreId);

    void deleteBookById(long id);

    void addAuthorToBook(long authorId, long bookId);

    void deleteAuthorFromBook(long authorId, long bookId);

    void addGenreToBook(long genreId, long bookId);

    void deleteGenreFromBook(long genreId, long bookId);
}
