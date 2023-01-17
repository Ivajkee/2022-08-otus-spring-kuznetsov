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

    void deleteBookById(long id);
}
