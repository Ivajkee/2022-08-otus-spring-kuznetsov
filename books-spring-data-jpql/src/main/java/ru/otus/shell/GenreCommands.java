package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.dto.GenreDto;
import ru.otus.service.GenreService;
import ru.otus.service.io.OutputService;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {
    private final GenreService genreService;
    private final OutputService outputService;

    @ShellMethod(value = "Show all genres.", key = {"all-g"})
    public void showGenres() {
        genreService.findAllGenres().forEach(genreDto -> outputService.output(String.format("%d: %s", genreDto.getId(), genreDto.getName())));
    }

    @ShellMethod(value = "Show genre.", key = {"g"})
    public void showGenre(@ShellOption long id) {
        GenreDto genreDto = genreService.findGenreById(id);
        outputService.output(String.format("%d: %s", genreDto.getId(), genreDto.getName()));
    }

    @ShellMethod(value = "Show count of genres.", key = {"count-g"})
    public void showCountOfGenres() {
        long count = genreService.getCountOfGenres();
        outputService.output(count);
    }

    @ShellMethod(value = "Add genre.", key = {"add-g"})
    public void addGenre(@ShellOption(arity = 3) String name) {
        GenreDto genreDto = new GenreDto(name);
        GenreDto addedGenre = genreService.saveGenre(genreDto);
        outputService.output(String.format("%d: %s", addedGenre.getId(), addedGenre.getName()));
    }

    @ShellMethod(value = "Edit genre.", key = {"edit-g"})
    public void editGenre(@ShellOption long id, @ShellOption(arity = 3) String name) {
        GenreDto genreDto = new GenreDto(id, name);
        GenreDto updatedGenre = genreService.updateGenre(genreDto);
        outputService.output(String.format("%d: %s", updatedGenre.getId(), updatedGenre.getName()));
    }

    @ShellMethod(value = "Delete genre.", key = {"del-g"})
    public void deleteGenre(@ShellOption long id) {
        genreService.deleteGenreById(id);
    }
}
