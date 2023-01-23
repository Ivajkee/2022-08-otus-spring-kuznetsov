package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private ConversionService conversionService;

    @DisplayName("Should return expected authors count")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long expectedCount = 3;
        when(authorRepository.count()).thenReturn(expectedCount);
        long actualCount = authorService.getCountOfAuthors();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("Should save author")
    @Test
    void shouldSaveAuthor() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto("New author");
        Author author = new Author(authorDto.getFullName());
        Author savedAuthor = new Author(id, author.getFullName());
        AuthorDto expectedAuthorDto = new AuthorDto(id, savedAuthor.getFullName());
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        when(conversionService.convert(authorDto, Author.class)).thenReturn(author);
        when(conversionService.convert(savedAuthor, AuthorDto.class)).thenReturn(expectedAuthorDto);
        AuthorDto actualAuthorDto = authorService.saveAuthor(authorDto);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should update author")
    @Test
    void shouldUpdateAuthor() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, "Edited author");
        Author author = new Author(id, authorDto.getFullName());
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(conversionService.convert(author, AuthorDto.class)).thenReturn(expectedAuthorDto);
        AuthorDto actualAuthorDto = authorService.updateAuthor(authorDto);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should throw exception when try update not existing author")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingAuthor() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, "Edited author");
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.updateAuthor(authorDto)).isInstanceOf(AuthorNotFoundException.class);
    }

    @DisplayName("Should be exist author")
    @Test
    void shouldBeExistAuthor() {
        long id = 1;
        when(authorRepository.existsById(id)).thenReturn(true);
        boolean authorIsExist = authorService.existsAuthorById(id);
        assertThat(authorIsExist).isTrue();
    }

    @DisplayName("Should be not exist author")
    @Test
    void shouldBeNotExistAuthor() {
        long id = 1;
        when(authorRepository.existsById(id)).thenReturn(false);
        boolean authorIsExist = authorService.existsAuthorById(id);
        assertThat(authorIsExist).isFalse();
    }

    @DisplayName("Should find author by id")
    @Test
    void shouldFindAuthorById() {
        long id = 1;
        Author author = new Author(id, "Test author");
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(conversionService.convert(author, AuthorDto.class)).thenReturn(expectedAuthorDto);
        AuthorDto actualAuthorDto = authorService.findAuthorById(id);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should throw exception when try find not existing author by id")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingAuthorById() {
        long id = 1;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.findAuthorById(id)).isInstanceOf(AuthorNotFoundException.class);
    }

    @DisplayName("Should find author by full name")
    @Test
    void shouldFindAuthorByFullName() {
        long id = 1;
        String fullName = "Test author";
        Author author = new Author(id, fullName);
        AuthorDto expectedAuthorDto = new AuthorDto(id, fullName);
        when(authorRepository.findByFullNameIgnoreCase(fullName)).thenReturn(Optional.of(author));
        when(conversionService.convert(author, AuthorDto.class)).thenReturn(expectedAuthorDto);
        AuthorDto actualAuthorDto = authorService.findAuthorByFullName(fullName);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should throw exception when try find not existing author by full name")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingAuthorByFullName() {
        String fullName = "Test author";
        when(authorRepository.findByFullNameIgnoreCase(fullName)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.findAuthorByFullName(fullName)).isInstanceOf(AuthorNotFoundException.class);
    }

    @DisplayName("Should find all authors")
    @Test
    void shouldFindAllAuthors() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Author author3 = new Author(3, "Test author 3");
        AuthorDto authorDto1 = new AuthorDto(author1.getId(), author1.getFullName());
        AuthorDto authorDto2 = new AuthorDto(author2.getId(), author2.getFullName());
        AuthorDto authorDto3 = new AuthorDto(author3.getId(), author3.getFullName());
        List<Author> authors = List.of(author1, author2, author3);
        List<AuthorDto> expectedAuthorDtoList = List.of(authorDto1, authorDto2, authorDto3);
        when(authorRepository.findAll()).thenReturn(authors);
        when(conversionService.convert(author1, AuthorDto.class)).thenReturn(authorDto1);
        when(conversionService.convert(author2, AuthorDto.class)).thenReturn(authorDto2);
        when(conversionService.convert(author3, AuthorDto.class)).thenReturn(authorDto3);
        List<AuthorDto> actualAuthorDtoList = authorService.findAllAuthors();
        assertThat(actualAuthorDtoList).isEqualTo(expectedAuthorDtoList);
    }

    @DisplayName("Should delete author")
    @Test
    void shouldDeleteAuthor() {
        assertThatCode(() -> authorService.deleteAuthorById(1)).doesNotThrowAnyException();
    }
}