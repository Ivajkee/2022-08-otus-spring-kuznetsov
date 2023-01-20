package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AuthorRepositoryJpa.class)
@DataJpaTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should return expected authors count")
    @Test
    void shouldReturnExpectedAuthorsCount() {
        long actualCount = authorRepository.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save author")
    @Test
    void shouldSaveAuthor() {
        Author author = new Author("Test author");
        Author expectedAuthor = authorRepository.save(author);
        Optional<Author> optionalActualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should update author")
    @Test
    void shouldUpdateAuthor() {
        Author author = new Author(3, "Edited author");
        Author expectedAuthor = authorRepository.update(author);
        Optional<Author> optionalActualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(optionalActualAuthor).get().extracting(Author::getFullName).isEqualTo(expectedAuthor.getFullName());
    }

    @DisplayName("Should be exist author")
    @Test
    void shouldBeExistAuthor() {
        boolean actualValue = authorRepository.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist author")
    @Test
    void shouldBeNotExistAuthor() {
        boolean actualValue = authorRepository.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find author by id")
    @Test
    void shouldFindAuthorById() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Optional<Author> optionalActualAuthor = authorRepository.findById(1);
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should find author by full name")
    @Test
    void shouldFindAuthorByFullName() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин");
        Optional<Author> optionalActualAuthor = authorRepository.findByFullName("александр сергеевич пушкин");
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }

    @DisplayName("Should find all authors")
    @Test
    void shouldFindAllAuthors() {
        Author expectedAuthor1 = new Author(1, "Александр Сергеевич Пушкин");
        Author expectedAuthor2 = new Author(2, "Лев Николаевич Толстой");
        Author expectedAuthor3 = new Author(3, "Джоан Роулинг");
        List<Author> expectedAuthors = List.of(expectedAuthor1, expectedAuthor2, expectedAuthor3);
        List<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }

    @DisplayName("Should delete author")
    @Test
    void shouldDeleteAuthor() {
        Author author = new Author("Test author");
        Author expectedAuthor = em.persistAndFlush(author);
        Optional<Author> optionalAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(optionalAuthor).hasValue(expectedAuthor);
        authorRepository.deleteById(expectedAuthor.getId());
        Optional<Author> optionalDeletedAuthor = authorRepository.findById(expectedAuthor.getId());
        assertThat(optionalDeletedAuthor).isEmpty();
    }
}