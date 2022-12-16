package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("books")
@RestController
public class BookController {
    private final BookService bookService;

    @PostMapping
    private BookDto save(@RequestBody BookDto bookDto) {
        log.info("Request save book {}", bookDto);
        return bookService.saveBook(bookDto);
    }

    @PutMapping
    private BookDto update(@RequestBody BookDto bookDto) {
        log.info("Request update book {}", bookDto);
        return bookService.updateBook(bookDto);
    }

    @GetMapping("/count")
    private long getCount() {
        log.info("Request get count of books");
        return bookService.getCountOfBooks();
    }

    @GetMapping("/{id}")
    private BookDto findById(@PathVariable long id) {
        log.info("Request find book by id {}", id);
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}")
    private boolean existsById(@PathVariable long id) {
        log.info("Request exists book by id {}", id);
        return bookService.existsBookById(id);
    }

    @GetMapping
    private List<BookDto> findAll() {
        log.info("Request find all books");
        return bookService.findAllBooks();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable long id) {
        log.info("Request delete book by id {}", id);
        bookService.deleteBookById(id);
    }
}