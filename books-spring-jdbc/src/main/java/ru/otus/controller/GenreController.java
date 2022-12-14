package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.dto.GenreDto;
import ru.otus.service.GenreService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("genres")
@RestController
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    private GenreDto save(@RequestBody GenreDto genreDto) {
        log.info("Request save genre {}", genreDto);
        return genreService.saveGenre(genreDto);
    }

    @GetMapping("/count")
    private long getCount() {
        log.info("Request get count of genres");
        return genreService.getCountOfGenres();
    }

    @GetMapping("/{id}")
    private GenreDto findById(@PathVariable long id) {
        log.info("Request find genre by id {}", id);
        return genreService.findGenreById(id);
    }

    @GetMapping
    private List<GenreDto> findAll() {
        log.info("Request find all genres");
        return genreService.findAllGenres();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable long id) {
        log.info("Request delete genre by id {}", id);
        genreService.deleteGenreById(id);
    }
}
