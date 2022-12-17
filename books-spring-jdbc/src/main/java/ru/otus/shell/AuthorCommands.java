package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.service.AuthorService;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {
    private final AuthorService authorService;

    @ShellMethod(value = "Show all authors.", key = {"all-a"})
    public void showAuthors() {
        authorService.findAllAuthors().forEach(authorDto -> log.info("{}: {}", authorDto.getId(), authorDto.getFullName()));
    }

    @ShellMethod(value = "Show author.", key = {"a"})
    public void showAuthor(@ShellOption long id) {
        AuthorDto authorDto = authorService.findAuthorById(id);
        log.info("{}: {}{}", authorDto.getId(), authorDto.getFullName(),
                authorDto.getBooks().stream()
                        .map(bookDto -> "\n" + bookDto.getTitle() + " (" + bookDto.getGenre().getName() + ")")
                        .collect(Collectors.joining()));
    }

    @ShellMethod(value = "Add author.", key = {"add-a"})
    public void addAuthor(@ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setFullName(fullName);
        AuthorDto addedAuthor = authorService.saveAuthor(authorDto);
        log.info("{}: {}", addedAuthor.getId(), addedAuthor.getFullName());
    }

    @ShellMethod(value = "Edit author.", key = {"edit-a"})
    public void editAuthor(@ShellOption long id, @ShellOption(arity = 3) String fullName) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorDto.setFullName(fullName);
        AuthorDto updatedAuthor = authorService.updateAuthor(authorDto);
        log.info("{}: {}", updatedAuthor.getId(), updatedAuthor.getFullName());
    }

    @ShellMethod(value = "Delete author.", key = {"del-a"})
    public void deleteAuthor(@ShellOption long id) {
        authorService.deleteAuthorById(id);
    }
}
