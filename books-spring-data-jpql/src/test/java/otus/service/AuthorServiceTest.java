package otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.config.ConversionServiceConfig;
import ru.otus.converter.AuthorDtoToAuthorConverter;
import ru.otus.converter.AuthorToAuthorDtoConverter;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.dto.AuthorDto;
import ru.otus.domain.model.Author;
import ru.otus.exception.AuthorNotFoundException;
import ru.otus.service.AuthorService;
import ru.otus.service.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AuthorServiceImpl.class, ConversionServiceConfig.class, AuthorDtoToAuthorConverter.class,
        AuthorToAuthorDtoConverter.class})
class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ConversionService conversionService;
    @MockBean
    private AuthorDao authorDao;

    @DisplayName("Should return expected authors count")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long expectedCount = 3;
        when(authorDao.count()).thenReturn(expectedCount);
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
        when(authorDao.save(author)).thenReturn(savedAuthor);
        AuthorDto expectedAuthorDto = new AuthorDto(id, savedAuthor.getFullName());
        AuthorDto actualAuthorDto = authorService.saveAuthor(authorDto);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should update author")
    @Test
    void shouldUpdateAuthor() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, "Edited author");
        Author author = new Author(id, authorDto.getFullName());
        when(authorDao.existsById(id)).thenReturn(true);
        when(authorDao.update(author)).thenReturn(author);
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        AuthorDto actualAuthorDto = authorService.updateAuthor(authorDto);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should throw exception when try update not existing author")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingAuthor() {
        long id = 1;
        AuthorDto authorDto = new AuthorDto(id, "Edited author");
        when(authorDao.existsById(id)).thenReturn(false);
        assertThatThrownBy(() -> authorService.updateAuthor(authorDto)).isInstanceOf(AuthorNotFoundException.class);
    }

    @DisplayName("Should be exist author")
    @Test
    void shouldBeExistAuthor() {
        long id = 1;
        when(authorDao.existsById(id)).thenReturn(true);
        boolean authorIsExist = authorService.existsAuthorById(id);
        assertThat(authorIsExist).isTrue();
    }

    @DisplayName("Should be not exist author")
    @Test
    void shouldBeNotExistAuthor() {
        long id = 1;
        when(authorDao.existsById(id)).thenReturn(false);
        boolean authorIsExist = authorService.existsAuthorById(id);
        assertThat(authorIsExist).isFalse();
    }

    @DisplayName("Should find author")
    @Test
    void shouldFindAuthor() {
        long id = 1;
        Author author = new Author(id, "Test author");
        when(authorDao.findById(id)).thenReturn(Optional.of(author));
        AuthorDto expectedAuthorDto = new AuthorDto(id, author.getFullName());
        AuthorDto actualAuthorDto = authorService.findAuthorById(id);
        assertThat(actualAuthorDto).isEqualTo(expectedAuthorDto);
    }

    @DisplayName("Should throw exception when try find not existing author")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingAuthor() {
        long id = 1;
        when(authorDao.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authorService.findAuthorById(id)).isInstanceOf(AuthorNotFoundException.class);
    }

    @DisplayName("Should find all authors")
    @Test
    void shouldFindAllAuthors() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Author author3 = new Author(3, "Test author 3");
        when(authorDao.findAll()).thenReturn(List.of(author1, author2, author3));
        AuthorDto authorDto1 = new AuthorDto(author1.getId(), author1.getFullName());
        AuthorDto authorDto2 = new AuthorDto(author2.getId(), author2.getFullName());
        AuthorDto authorDto3 = new AuthorDto(author3.getId(), author3.getFullName());
        List<AuthorDto> expectedAuthorDtoList = List.of(authorDto1, authorDto2, authorDto3);
        List<AuthorDto> actualAuthorDtoList = authorService.findAllAuthors();
        assertThat(actualAuthorDtoList).isEqualTo(expectedAuthorDtoList);
    }

    @DisplayName("Should delete author")
    @Test
    void shouldDeleteAuthor() {
        assertThatCode(() -> authorService.deleteAuthorById(1)).doesNotThrowAnyException();
    }
}