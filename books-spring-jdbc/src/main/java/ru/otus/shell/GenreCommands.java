package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.GenreDto;
import ru.otus.service.GenreService;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "Show all genres.", key = {"all-g"})
    public void showGenres() {
        genreService.findAllGenres().forEach(genreDto -> log.info("{}: {}", genreDto.getId(), genreDto.getName()));
    }

    @ShellMethod(value = "Show genre.", key = {"g"})
    public void showGenre(@ShellOption long id) {
        GenreDto genreDto = genreService.findGenreById(id);
        log.info("{}: {}", genreDto.getId(), genreDto.getName());
        genreDto.getBooks().forEach(bookDto -> log.info("{} ({})", bookDto.getTitle(), bookDto.getAuthor().getFullName()));
    }

    @ShellMethod(value = "Add genre.", key = {"add-g"})
    public void addGenre(@ShellOption(arity = 3) String name) {
        GenreDto genreDto = new GenreDto();
        genreDto.setName(name);
        GenreDto addedGenre = genreService.saveGenre(genreDto);
        log.info("{}: {}", addedGenre.getId(), addedGenre.getName());
    }

    @ShellMethod(value = "Edit genre.", key = {"edit-g"})
    public void editGenre(@ShellOption long id, @ShellOption(arity = 3) String name) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(id);
        genreDto.setName(name);
        GenreDto updatedGenre = genreService.updateGenre(genreDto);
        log.info("{}: {}", updatedGenre.getId(), updatedGenre.getName());
    }

    @ShellMethod(value = "Delete genre.", key = {"del-g"})
    public void deleteGenre(@ShellOption long id) {
        genreService.deleteGenreById(id);
    }
}
