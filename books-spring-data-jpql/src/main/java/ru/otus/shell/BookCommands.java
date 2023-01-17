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

    @ShellMethod(value = "Show book by id.", key = {"b"})
    public void showBook(@ShellOption long id) {
        BookDto bookDto = bookService.findBookById(id);
        printBook(bookDto);
    }

    @ShellMethod(value = "Show book by title.", key = {"b-title"})
    public void showBook(@ShellOption String title) {
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
    public void editBook(@ShellOption long id, @ShellOption(arity = 5) String title) {
        BookDto bookDto = new BookDto(id, title.trim());
        BookDto updatedBook = bookService.updateBook(bookDto);
        printBook(updatedBook);
    }

    @ShellMethod(value = "Delete book.", key = {"del-b"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteBookById(id);
    }

    private void printBook(BookDto bookDto) {
        outputService.output(String.format("%d: %s (Авторы: %s, Жанры: %s, Комментарии: %s)", bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthors().stream().map(authorDto -> String.format("%d: %s", authorDto.getId(), authorDto.getFullName())).toList(),
                bookDto.getGenres().stream().map(genreDto -> String.format("%d: %s", genreDto.getId(), genreDto.getName())).toList(),
                bookDto.getComments().stream().map(commentDto -> String.format("%d: %s", commentDto.getId(), commentDto.getText())).toList()));
    }
}
