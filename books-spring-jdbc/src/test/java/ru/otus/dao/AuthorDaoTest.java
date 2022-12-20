package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AuthorDaoJdbc.class)
@JdbcTest
class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @DisplayName("Should return number of five authors")
    @Test
    void shouldReturnNumberOfFiveAuthors() {
        long actualCount = authorDao.count();
        assertThat(actualCount).isEqualTo(5);
    }

    @DisplayName("Should save one author")
    @Test
    void shouldSaveOneAuthor() {
        Author expectedAuthor = new Author("Test author", List.of());
        expectedAuthor = authorDao.save(expectedAuthor);
        Optional<Author> optionalActualAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should update one author")
    @Test
    void shouldUpdateOneAuthor() {
        Author expectedAuthor = new Author(5L, "Edited author", null);
        expectedAuthor = authorDao.update(expectedAuthor);
        Optional<Author> optionalActualAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).get().extracting(Author::getFullName).isEqualTo(expectedAuthor.getFullName());
    }

    @DisplayName("Should be exist author")
    @Test
    void shouldBeExistAuthor() {
        boolean actualValue = authorDao.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist author")
    @Test
    void shouldBeNotExistAuthor() {
        boolean actualValue = authorDao.existsById(6);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find one author")
    @Test
    void shouldFindOneAuthor() {
        Genre expectedGenre = new Genre(1, "Поэма", null);
        Book expectedBook = new Book(1, "Руслан и Людмила", null, expectedGenre);
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", List.of(expectedBook));
        Optional<Author> optionalActualAuthor = authorDao.findById(1);
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should find all authors")
    @Test
    void shouldFindAllAuthors() {
        Author expectedAuthor1 = new Author(1, "Александр Сергеевич Пушкин", null);
        Author expectedAuthor2 = new Author(2, "Лев Николаевич Толстой", null);
        Author expectedAuthor3 = new Author(3, "Джоан Роулинг", null);
        Author expectedAuthor4 = new Author(4, "Агата Кристи", null);
        Author expectedAuthor5 = new Author(5, "Стивен Кинг", null);
        List<Author> expectedAuthors = List.of(expectedAuthor1, expectedAuthor2, expectedAuthor3, expectedAuthor4, expectedAuthor5);
        List<Author> actualAuthors = authorDao.findAll();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }

    @DisplayName("Should delete one author")
    @Test
    void shouldDeleteOneAuthor() {
        Author expectedAuthor = new Author("Test author", List.of());
        expectedAuthor = authorDao.save(expectedAuthor);
        Optional<Author> optionalAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalAuthor).hasValue(expectedAuthor);
        authorDao.deleteById(expectedAuthor.getId());
        Optional<Author> optionalDeletedAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}