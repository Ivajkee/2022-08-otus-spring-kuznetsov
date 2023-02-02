package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.BookDto;
import ru.otus.service.BookService;
import ru.otus.service.out.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {
    private final BookService bookService;
    private final OutputService outputService;

    @ShellMethod(value = "Show all books.", key = {"all-b"})
    public void showBooks() {
        bookService.findAllBooks().forEach(this::printBook);
    }

    @ShellMethod(value = "Show all books by author id.", key = {"all-b-a"})
    public void showBooksByAuthor(@ShellOption String authorId) {
        bookService.findAllBooksByAuthor(authorId).forEach(this::printBook);
    }

    @ShellMethod(value = "Show all books by genre id.", key = {"all-b-g"})
    public void showBooksByGenre(@ShellOption String genreId) {
        bookService.findAllBooksByGenre(genreId).forEach(this::printBook);
    }

    @ShellMethod(value = "Show book by id.", key = {"b"})
    public void showBookById(@ShellOption String id) {
        BookDto bookDto = bookService.findBookById(id);
        printBook(bookDto);
    }

    @ShellMethod(value = "Show book by title.", key = {"b-title"})
    public void showBookByTitle(@ShellOption String title) {
        BookDto bookDto = bookService.findBookByTitle(title.trim());
        printBook(bookDto);
    }

    @ShellMethod(value = "Show count of books.", key = {"count-b"})
    public void showCountOfBooks() {
        long count = bookService.getCountOfBooks();
        outputService.output(count);
    }

    @ShellMethod(value = "Add book.", key = {"add-b"})
    public void addBook(@ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(title.trim());
        BookDto addedBook = bookService.saveBook(bookDto);
        printBook(addedBook);
    }

    @ShellMethod(value = "Edit book.", key = {"edit-b"})
    public void editBook(@ShellOption String id, @ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(id, title.trim());
        BookDto updatedBook = bookService.updateBook(bookDto);
        printBook(updatedBook);
    }

    @ShellMethod(value = "Delete book.", key = {"del-b"})
    public void deleteBook(@ShellOption String id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "Add author to book.", key = {"add-a-to-b"})
    public void addAuthorToBook(@ShellOption String authorId, @ShellOption String bookId) {
        bookService.addAuthorToBook(authorId, bookId);
    }

    @ShellMethod(value = "Delete author from book.", key = {"del-a-from-b"})
    public void deleteAuthorFromBook(@ShellOption String authorId, @ShellOption String bookId) {
        bookService.deleteAuthorFromBook(authorId, bookId);
    }

    @ShellMethod(value = "Add genre to book.", key = {"add-g-to-b"})
    public void addGenreToBook(@ShellOption String genreId, @ShellOption String bookId) {
        bookService.addGenreToBook(genreId, bookId);
    }

    @ShellMethod(value = "Delete genre from book.", key = {"del-g-from-b"})
    public void deleteGenreFromBook(@ShellOption String genreId, @ShellOption String bookId) {
        bookService.deleteGenreFromBook(genreId, bookId);
    }

    private void printBook(BookDto bookDto) {
        outputService.output(String.format("%s: %s (Авторы: %s, Жанры: %s)", bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthors().stream().map(authorDto -> String.format("%s: %s", authorDto.getId(), authorDto.getFullName())).toList(),
                bookDto.getGenres().stream().map(genreDto -> String.format("%s: %s", genreDto.getId(), genreDto.getName())).toList()));
    }
}
