package ru.otus.service;

import ru.otus.domain.dto.BookDto;

import java.util.List;

public interface BookService {
    long getCountOfBooks();

    BookDto saveBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    boolean existsBookById(String id);

    BookDto findBookById(String id);

    BookDto findBookByTitle(String title);

    List<BookDto> findAllBooks();

    List<BookDto> findAllBooksByAuthor(String authorId);

    List<BookDto> findAllBooksByGenre(String genreId);

    void deleteBookById(String id);

    void addAuthorToBook(String authorId, String bookId);

    void deleteAuthorFromBook(String authorId, String bookId);

    void addGenreToBook(String genreId, String bookId);

    void deleteGenreFromBook(String genreId, String bookId);
}
