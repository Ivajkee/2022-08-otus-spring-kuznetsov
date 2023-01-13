package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ConversionService conversionService;

    @Override
    public long getCountOfBooks() {
        long count = bookRepository.count();
        log.debug("Found {} books", count);
        return count;
    }

    @Transactional
    @Override
    public BookDto saveBook(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        Book savedBook = bookRepository.save(book);
        BookDto savedBookDto = conversionService.convert(savedBook, BookDto.class);
        log.debug("Saved book: {}", savedBookDto);
        return savedBookDto;
    }

    @Transactional
    @Override
    public BookDto updateBook(BookDto bookDto) {
        BookDto updatedBookDto = bookRepository.findById(bookDto.getId()).map(book -> {
            book.setTitle(bookDto.getTitle());
            return conversionService.convert(book, BookDto.class);
        }).orElseThrow(() -> new BookNotFoundException(bookDto.getId()));
        log.debug("Updated book: {}", updatedBookDto);
        return updatedBookDto;
    }

    @Override
    public boolean existsBookById(long id) {
        boolean bookIsExist = bookRepository.existsById(id);
        log.debug("Book with id {} is exist: {}", id, bookIsExist);
        return bookIsExist;
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        log.debug("Found book: {}", bookDto);
        return bookDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> booksDto = books.stream()
                .map(book -> conversionService.convert(book, BookDto.class))
                .collect(Collectors.toList());
        log.debug("Found books: {}", booksDto);
        return booksDto;
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
        log.debug("Book with id {} deleted", id);
    }
}
