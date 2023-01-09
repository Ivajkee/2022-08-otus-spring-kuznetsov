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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final ConversionService conversionService;

    @Override
    public long getCountOfBooks() {
        long count = bookDao.count();
        log.debug("Found {} books", count);
        return count;
    }

    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        Book savedBook = bookDao.save(book);
        BookDto savedBookDto = conversionService.convert(savedBook, BookDto.class);
        log.debug("Saved book: {}", savedBookDto);
        return savedBookDto;
    }

    @Transactional
    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book updatedBook = Optional.of(bookDto)
                .filter(dto -> bookDao.existsById(dto.getId()))
                .map(dto -> conversionService.convert(dto, Book.class))
                .map(bookDao::update)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookDto.getId() + " not found!"));
        BookDto updatedBookDto = conversionService.convert(updatedBook, BookDto.class);
        log.debug("Updated book: {}", updatedBookDto);
        return updatedBookDto;
    }

    @Override
    public boolean existsBookById(long id) {
        boolean bookIsExist = bookDao.existsById(id);
        log.debug("Book with id {} is exist: {}", id, bookIsExist);
        return bookIsExist;
    }

    @Override
    public BookDto findBookById(long id) {
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found!"));
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        log.debug("Found book: {}", bookDto);
        return bookDto;
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookDao.findAll();
        List<BookDto> booksDto = books.stream()
                .map(book -> conversionService.convert(book, BookDto.class))
                .collect(Collectors.toList());
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
        log.debug("Book with id {} deleted", id);
    }
}
