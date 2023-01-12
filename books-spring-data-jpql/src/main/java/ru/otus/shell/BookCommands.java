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
        bookService.findAllBooks().forEach(this::printBook);
    }

    @ShellMethod(value = "Show book.", key = {"b"})
    public void showBook(@ShellOption long id) {
        BookDto bookDto = bookService.findBookById(id);
        printBook(bookDto);
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
        printBook(addedBook);
    }

    @ShellMethod(value = "Edit book.", key = {"edit-b"})
    public void editBook(@ShellOption long id, @ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(id, title);
        BookDto updatedBook = bookService.updateBook(bookDto);
        printBook(updatedBook);
    }

    @ShellMethod(value = "Delete book.", key = {"del-b"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Add author to book.", key = {"add-a-to-b"})
    public void addAuthorToBook(@ShellOption long bookId, @ShellOption long authorId) {
        BookDto bookDto = bookService.addAuthorToBook(bookId, authorId);
        printBook(bookDto);
    }

    @ShellMethod(value = "Delete author from book.", key = {"del-a-from-b"})
    public void deleteAuthorFromBook(@ShellOption long bookId, @ShellOption long authorId) {
        BookDto bookDto = bookService.deleteAuthorFromBook(bookId, authorId);
        printBook(bookDto);
    }

    @ShellMethod(value = "Add genre to book.", key = {"add-g-to-b"})
    public void addGenreToBook(@ShellOption long bookId, @ShellOption long genreId) {
        BookDto bookDto = bookService.addGenreToBook(bookId, genreId);
        printBook(bookDto);
    }

    @ShellMethod(value = "Delete genre from book.", key = {"del-g-from-b"})
    public void deleteGenreFromBook(@ShellOption long bookId, @ShellOption long genreId) {
        BookDto bookDto = bookService.deleteGenreFromBook(bookId, genreId);
        printBook(bookDto);
    }

    private void printBook(BookDto bookDto) {
        outputService.output(String.format("%d: %s (Авторы: %s, Жанры: %s)", bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthors().stream().map(AuthorDto::getFullName).toList(),
                bookDto.getGenres().stream().map(GenreDto::getName).toList()));
    }
}
