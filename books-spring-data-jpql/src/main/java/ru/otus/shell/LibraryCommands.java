package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.CommentDto;
import ru.otus.service.LibraryService;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommands {
    private final LibraryService libraryService;

    @ShellMethod(value = "Add author to book.", key = {"add-a-to-b"})
    public void addAuthorToBook(@ShellOption long authorId, @ShellOption long bookId) {
        libraryService.addAuthorToBook(authorId, bookId);
    }

    @ShellMethod(value = "Delete author from book.", key = {"del-a-from-b"})
    public void deleteAuthorFromBook(@ShellOption long authorId, @ShellOption long bookId) {
        libraryService.deleteAuthorFromBook(authorId, bookId);
    }

    @ShellMethod(value = "Add genre to book.", key = {"add-g-to-b"})
    public void addGenreToBook(@ShellOption long genreId, @ShellOption long bookId) {
        libraryService.addGenreToBook(genreId, bookId);
    }

    @ShellMethod(value = "Delete genre from book.", key = {"del-g-from-b"})
    public void deleteGenreFromBook(@ShellOption long genreId, @ShellOption long bookId) {
        libraryService.deleteGenreFromBook(genreId, bookId);
    }

    @ShellMethod(value = "Add comment to book.", key = {"add-c-to-b"})
    public void addCommentToBook(@ShellOption long bookId, @ShellOption String text) {
        CommentDto commentDto = new CommentDto(text);
        libraryService.addCommentToBook(bookId, commentDto);
    }
}
