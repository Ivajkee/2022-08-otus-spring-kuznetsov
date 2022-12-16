package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final ConversionService conversionService;

    @Override
    public long getCountOfAuthors() {
        return authorDao.count();
    }

    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        Author author = conversionService.convert(authorDto, Author.class);
        Author savedAuthor = authorDao.save(author);
        return conversionService.convert(savedAuthor, AuthorDto.class);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        Author updatedAuthor = Optional.of(authorDto)
                .filter(author -> authorDao.existsById(author.getId()))
                .map(bd -> conversionService.convert(bd, Author.class))
                .map(authorDao::update)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + authorDto.getId() + " not found!"));
        return conversionService.convert(updatedAuthor, AuthorDto.class);
    }

    @Override
    public boolean existsAuthorById(long id) {
        return authorDao.existsById(id);
    }

    @Override
    public AuthorDto findAuthorById(long id) {
        Author author = authorDao.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " not found!"));
        return conversionService.convert(author, AuthorDto.class);
    }

    @Override
    public List<AuthorDto> findAllAuthors() {
        List<Author> authors = authorDao.findAll();
        List<AuthorDto> authorsDto = authors.stream()
                .map(author -> conversionService.convert(author, AuthorDto.class))
                .collect(Collectors.toList());
        log.info("Found {} authors", authorsDto.size());
        return authorsDto;
    }

    @Override
    public void deleteAuthorById(long id) {
        authorDao.deleteById(id);
        log.info("Author with id {} deleted", id);
    }
}
