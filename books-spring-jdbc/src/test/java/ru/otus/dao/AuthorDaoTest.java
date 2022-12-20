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

    @DisplayName("Should return expected authors count")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long actualCount = authorDao.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save author")
    @Test
    void shouldSaveAuthor() {
        Author expectedAuthor = new Author("Test author", List.of());
        expectedAuthor = authorDao.save(expectedAuthor);
        Optional<Author> optionalActualAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should update author")
    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author(3, "Edited author", null);
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
        boolean actualValue = authorDao.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find author")
    @Test
    void shouldFindAuthor() {
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
        List<Author> expectedAuthors = List.of(expectedAuthor1, expectedAuthor2, expectedAuthor3);
        List<Author> actualAuthors = authorDao.findAll();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }

    @DisplayName("Should delete author")
    @Test
    void shouldDeleteAuthor() {
        Author expectedAuthor = new Author("Test author", List.of());
        expectedAuthor = authorDao.save(expectedAuthor);
        Optional<Author> optionalAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalAuthor).hasValue(expectedAuthor);
        authorDao.deleteById(expectedAuthor.getId());
        Optional<Author> optionalDeletedAuthor = authorDao.findById(expectedAuthor.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}