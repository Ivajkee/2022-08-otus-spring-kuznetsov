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

import java.util.stream.Collectors;

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

    private void printBook(BookDto bookDto) {
        outputService.output(String.format("%d: %s (Авторы: %s, Жанры: %s)\nКомментарии:\n%s", bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthors().stream().map(AuthorDto::getFullName).toList(),
                bookDto.getGenres().stream().map(GenreDto::getName).toList(),
                bookDto.getComments().stream().map(commentDto -> String.format("%d: %s", commentDto.getId(), commentDto.getText()))
                        .collect(Collectors.joining("\n"))));
    }
}
