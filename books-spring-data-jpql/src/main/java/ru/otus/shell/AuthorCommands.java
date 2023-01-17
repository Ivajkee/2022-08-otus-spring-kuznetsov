package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.service.AuthorService;
import ru.otus.service.out.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {
    private final AuthorService authorService;
    private final OutputService outputService;

    @ShellMethod(value = "Show all authors.", key = {"all-a"})
    public void showAuthors() {
        authorService.findAllAuthors().forEach(this::printAuthor);
    }

    @ShellMethod(value = "Show author by id.", key = {"a"})
    public void showAuthor(@ShellOption long id) {
        AuthorDto authorDto = authorService.findAuthorById(id);
        printAuthor(authorDto);
    }

    @ShellMethod(value = "Show author by name.", key = {"a-name"})
    public void showAuthor(@ShellOption String fullName) {
        AuthorDto authorDto = authorService.findAuthorByFullName(fullName.trim());
        printAuthor(authorDto);
    }

    @ShellMethod(value = "Show count of authors.", key = {"count-a"})
    public void showCountOfAuthors() {
        long count = authorService.getCountOfAuthors();
        outputService.output(count);
    }

    @ShellMethod(value = "Add author.", key = {"add-a"})
    public void addAuthor(@ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto(fullName.trim());
        AuthorDto addedAuthor = authorService.saveAuthor(authorDto);
        printAuthor(addedAuthor);
    }


    @ShellMethod(value = "Edit author.", key = {"edit-a"})
    public void editAuthor(@ShellOption long id, @ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto(id, fullName.trim());
        AuthorDto updatedAuthor = authorService.updateAuthor(authorDto);
        printAuthor(updatedAuthor);
    }

    @ShellMethod(value = "Delete author.", key = {"del-a"})
    public void deleteAuthor(@ShellOption long id) {
        authorService.deleteAuthorById(id);
    }

    private void printAuthor(AuthorDto authorDto) {
        outputService.output(String.format("%d: %s (Книги: %s)", authorDto.getId(), authorDto.getFullName(),
                authorDto.getBooks().stream().map(bookDto -> String.format("%d: %s", bookDto.getId(), bookDto.getTitle())).toList()));
    }
}
