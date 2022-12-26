package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreDaoJdbc.class)
@JdbcTest
class GenreDaoTest {
    @Autowired
    private GenreDao genreDao;

    @DisplayName("Should return expected genres count")
    @Test
    void shouldReturnExpectedGenresCount() {
        long actualCount = genreDao.count();
        assertThat(actualCount).isEqualTo(3);
    }

    @DisplayName("Should save genre")
    @Test
    void shouldSaveGenre() {
        Genre expectedGenre = new Genre("Test genre");
        expectedGenre = genreDao.save(expectedGenre);
        Optional<Genre> optionalActualGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should update genre")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre(3, "Edited genre");
        expectedGenre = genreDao.update(expectedGenre);
        Optional<Genre> optionalActualGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).get().extracting(Genre::getName).isEqualTo(expectedGenre.getName());
    }

    @DisplayName("Should be exist genre")
    @Test
    void shouldBeExistGenre() {
        boolean actualValue = genreDao.existsById(1);
        assertThat(actualValue).isTrue();
    }

    @DisplayName("Should be not exist genre")
    @Test
    void shouldBeNotExistGenre() {
        boolean actualValue = genreDao.existsById(4);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find genre")
    @Test
    void shouldFindGenre() {
        Genre expectedGenre = new Genre(1, "Поэма");
        Optional<Genre> optionalActualGenre = genreDao.findById(1);
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should find all genres")
    @Test
    void shouldFindAllGenres() {
        Genre expectedGenre1 = new Genre(1, "Поэма");
        Genre expectedGenre2 = new Genre(2, "Роман");
        Genre expectedGenre3 = new Genre(3, "Фэнтези");
        List<Genre> expectedGenres = List.of(expectedGenre1, expectedGenre2, expectedGenre3);
        List<Genre> actualGenres = genreDao.findAll();
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

    @DisplayName("Should delete genre")
    @Test
    void shouldDeleteGenre() {
        Genre expectedGenre = new Genre("Test genre");
        expectedGenre = genreDao.save(expectedGenre);
        Optional<Genre> optionalGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalGenre).hasValue(expectedGenre);
        genreDao.deleteById(expectedGenre.getId());
        Optional<Genre> optionalDeletedGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalDeletedGenre).isEmpty();
    }
}