package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreRepositoryJpa.class)
@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should return expected genres count")
    @Test
    void shouldReturnExpectedGenresCount() {
        long actualCount = genreRepository.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save genre")
    @Test
    void shouldSaveGenre() {
        Genre expectedGenre = new Genre("Test genre");
        expectedGenre = genreRepository.save(expectedGenre);
        Optional<Genre> optionalActualGenre = genreRepository.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should update genre")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre(3, "Edited genre");
        expectedGenre = genreRepository.update(expectedGenre);
        Optional<Genre> optionalActualGenre = genreRepository.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).get().extracting(Genre::getName).isEqualTo(expectedGenre.getName());
    }

    @DisplayName("Should be exist genre")
    @Test
    void shouldBeExistGenre() {
        boolean actualValue = genreRepository.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist genre")
    @Test
    void shouldBeNotExistGenre() {
        boolean actualValue = genreRepository.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find genre by id")
    @Test
    void shouldFindGenreById() {
        Genre expectedGenre = new Genre(1, "Поэма");
        Optional<Genre> optionalActualGenre = genreRepository.findById(1);
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should find genre by name")
    @Test
    void shouldFindGenreByName() {
        Genre expectedGenre = new Genre(1, "Поэма");
        Optional<Genre> optionalActualGenre = genreRepository.findByName("поэма");
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should find all genres")
    @Test
    void shouldFindAllGenres() {
        Genre expectedGenre1 = new Genre(1, "Поэма");
        Genre expectedGenre2 = new Genre(2, "Роман");
        Genre expectedGenre3 = new Genre(3, "Фэнтези");
        List<Genre> expectedGenres = List.of(expectedGenre1, expectedGenre2, expectedGenre3);
        List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

    @DisplayName("Should delete genre")
    @Test
    void shouldDeleteGenre() {
        Genre genre = new Genre("Test genre");
        Genre expectedGenre = em.persistAndFlush(genre);
        Optional<Genre> optionalGenre = genreRepository.findById(expectedGenre.getId());
        assertThat(optionalGenre).hasValue(expectedGenre);
        genreRepository.deleteById(expectedGenre.getId());
        Optional<Genre> optionalDeletedGenre = genreRepository.findById(expectedGenre.getId());
        assertThat(optionalDeletedGenre).isEmpty();
    }
}