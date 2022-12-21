package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.service.BookService;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;

    @ShellMethod(value = "Show all books.", key = {"all-b"})
    public void showBooks() {
        bookService.findAllBooks().forEach(bookDto -> log.info("{}: {}", bookDto.getId(), bookDto.getTitle()));
    }

    @ShellMethod(value = "Show book.", key = {"b"})
    public void showBook(@ShellOption long id) {
        BookDto bookDto = bookService.findBookById(id);
        log.info("{}: {} ({}, {})", bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor().getFullName(), bookDto.getGenre().getName());
    }

    @ShellMethod(value = "Show count of books.", key = {"count-b"})
    public void showCountOfBooks() {
        long count = bookService.getCountOfBooks();
        log.info("{}", count);
    }

    @ShellMethod(value = "Add book.", key = {"add-b"})
    public void addBook(@ShellOption long authorId, @ShellOption long genreId, @ShellOption(arity = 10) String title) {
        AuthorDto authorDto = new AuthorDto(authorId, null, null);
        GenreDto genreDto = new GenreDto(genreId, null, null);
        BookDto bookDto = new BookDto(title, authorDto, genreDto);
        BookDto addedBook = bookService.saveBook(bookDto);
        log.info("{}: {}", addedBook.getId(), addedBook.getTitle());
    }

    @ShellMethod(value = "Edit book.", key = {"edit-b"})
    public void editBook(@ShellOption long id, @ShellOption long authorId, @ShellOption long genreId, @ShellOption(arity = 10) String title) {
        AuthorDto authorDto = new AuthorDto(authorId, null, null);
        GenreDto genreDto = new GenreDto(genreId, null, null);
        BookDto bookDto = new BookDto(id, title, authorDto, genreDto);
        BookDto updatedBook = bookService.updateBook(bookDto);
        log.info("{}: {}", updatedBook.getId(), updatedBook.getTitle());
    }

    @ShellMethod(value = "Delete book.", key = {"del-b"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteBookById(id);
    }
}
