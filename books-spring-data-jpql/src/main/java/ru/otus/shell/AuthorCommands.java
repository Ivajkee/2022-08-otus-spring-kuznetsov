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
        authorService.findAllAuthors().forEach(authorDto -> outputService.output(String.format("%d: %s", authorDto.getId(),
                authorDto.getFullName())));
    }

    @ShellMethod(value = "Show author.", key = {"a"})
    public void showAuthor(@ShellOption long id) {
        AuthorDto authorDto = authorService.findAuthorById(id);
        outputService.output(String.format("%d: %s", authorDto.getId(), authorDto.getFullName()));
    }

    @ShellMethod(value = "Show count of authors.", key = {"count-a"})
    public void showCountOfAuthors() {
        long count = authorService.getCountOfAuthors();
        outputService.output(count);
    }

    @ShellMethod(value = "Add author.", key = {"add-a"})
    public void addAuthor(@ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto(fullName);
        AuthorDto addedAuthor = authorService.saveAuthor(authorDto);
        outputService.output(String.format("%d: %s", addedAuthor.getId(), addedAuthor.getFullName()));
    }

    @ShellMethod(value = "Edit author.", key = {"edit-a"})
    public void editAuthor(@ShellOption long id, @ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto(id, fullName);
        AuthorDto updatedAuthor = authorService.updateAuthor(authorDto);
        outputService.output(String.format("%d: %s", updatedAuthor.getId(), updatedAuthor.getFullName()));
    }

    @ShellMethod(value = "Delete author.", key = {"del-a"})
    public void deleteAuthor(@ShellOption long id) {
        authorService.deleteAuthorById(id);
    }
}
