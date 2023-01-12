package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.dto.GenreDto;
import ru.otus.service.BookService;
import ru.otus.service.io.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;
    private final OutputService outputService;

    @ShellMethod(value = "Show all books.", key = {"all-b"})
    public void showBooks() {
        bookService.findAllBooks().forEach(bookDto -> outputService.output(String.format("%d: %s (%s, %s)", bookDto.getId(),
                bookDto.getTitle(), bookDto.getAuthors().stream().map(AuthorDto::getFullName).toList(),
                bookDto.getGenres().stream().map(GenreDto::getName).toList())));
    }

    @ShellMethod(value = "Show book.", key = {"b"})
    public void showBook(@ShellOption long id) {
        BookDto bookDto = bookService.findBookById(id);
        outputService.output(String.format("%d: %s (%s, %s)", bookDto.getId(), bookDto.getTitle(), bookDto.getAuthors()
                .stream().map(AuthorDto::getFullName).toList(), bookDto.getGenres().stream().map(GenreDto::getName).toList()));
    }

    @ShellMethod(value = "Show count of books.", key = {"count-b"})
    public void showCountOfBooks() {
        long count = bookService.getCountOfBooks();
        outputService.output(count);
    }

    @ShellMethod(value = "Add book.", key = {"add-b"})
    public void addBook(@ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(title);
        BookDto addedBook = bookService.saveBook(bookDto);
        outputService.output(String.format("%d: %s", addedBook.getId(), addedBook.getTitle()));
    }

    @ShellMethod(value = "Edit book.", key = {"edit-b"})
    public void editBook(@ShellOption long id, @ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(id, title);
        BookDto updatedBook = bookService.updateBook(bookDto);
        outputService.output(String.format("%d: %s", updatedBook.getId(), updatedBook.getTitle()));
    }

    @ShellMethod(value = "Delete book.", key = {"del-b"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Add author to book.", key = {"add-a-to-b"})
    public void addAuthorToBook(@ShellOption long bookId, @ShellOption long authorId) {
        bookService.addAuthorToBook(bookId, authorId);
    }

    @ShellMethod(value = "Delete author from book.", key = {"del-a-from-b"})
    public void deleteAuthorFromBook(@ShellOption long bookId, @ShellOption long authorId) {
        bookService.deleteAuthorFromBook(bookId, authorId);
    }

    @ShellMethod(value = "Add genre to book.", key = {"add-g-to-b"})
    public void addGenreToBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.addGenreToBook(bookId, genreId);
    }

    @ShellMethod(value = "Delete genre from book.", key = {"del-g-from-b"})
    public void deleteGenreFromBook(@ShellOption long bookId, @ShellOption long genreId) {
        bookService.deleteGenreFromBook(bookId, genreId);
    }
}
