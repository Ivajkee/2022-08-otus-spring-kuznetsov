package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.service.AuthorService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("authors")
@RestController
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    private AuthorDto save(@RequestBody AuthorDto authorDto) {
        log.info("Request save author {}", authorDto);
        return authorService.saveAuthor(authorDto);
    }

    @PutMapping
    private AuthorDto update(@RequestBody AuthorDto authorDto) {
        log.info("Request update author {}", authorDto);
        return authorService.updateAuthor(authorDto);
    }

    @GetMapping("/count")
    private long getCount() {
        log.info("Request get count of authors");
        return authorService.getCountOfAuthors();
    }

    @GetMapping("/{id}")
    private AuthorDto findById(@PathVariable long id) {
        log.info("Request find author by id {}", id);
        return authorService.findAuthorById(id);
    }

    @PutMapping("/{id}")
    private boolean existsById(@PathVariable long id) {
        log.info("Request exists author by id {}", id);
        return authorService.existsAuthorById(id);
    }

    @GetMapping
    private List<AuthorDto> findAll() {
        log.info("Request find all authors");
        return authorService.findAllAuthors();
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable long id) {
        log.info("Request delete author by id {}", id);
        authorService.deleteAuthorById(id);
    }
}
