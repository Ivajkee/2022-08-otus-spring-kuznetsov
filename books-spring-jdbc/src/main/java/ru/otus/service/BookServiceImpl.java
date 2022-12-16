package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Book;
import ru.otus.exception.BookNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final ConversionService conversionService;

    @Override
    public long getCountOfBooks() {
        return bookDao.count();
    }

    @Transactional
    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        Book savedBook = bookDao.save(book);
        return conversionService.convert(savedBook, BookDto.class);
    }

    @Transactional
    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        Book updatedBook = bookDao.findById(Objects.requireNonNull(book).getId())
                .map(updatableBook -> {
                    updatableBook.setTitle(book.getTitle());
                    updatableBook.setAuthor(book.getAuthor());
                    updatableBook.setGenre(book.getGenre());
                    return bookDao.update(updatableBook);
                })
                .orElseThrow(() -> new BookNotFoundException("Book with id " + book.getId() + " not found!"));
        return conversionService.convert(updatedBook, BookDto.class);
    }

    @Override
    public BookDto findBookById(long id) {
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found!"));
        return conversionService.convert(book, BookDto.class);
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookDao.findAll();
        List<BookDto> booksDto = books.stream()
                .map(book -> conversionService.convert(book, BookDto.class))
                .collect(Collectors.toList());
        log.info("Found {} books", booksDto.size());
        return booksDto;
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
        log.info("Book with id {} deleted", id);
    }
}
