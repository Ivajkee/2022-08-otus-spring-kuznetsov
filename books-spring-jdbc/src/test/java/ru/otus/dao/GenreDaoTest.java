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

@Import(GenreDaoJdbc.class)
@JdbcTest
class GenreDaoTest {
    @Autowired
    private GenreDao genreDao;

    @DisplayName("Should return number of five genres")
    @Test
    void shouldReturnNumberOfFiveGenres() {
        long actualCount = genreDao.count();
        assertThat(actualCount).isEqualTo(5);
    }

    @DisplayName("Should save one genre")
    @Test
    void shouldSaveOneGenre() {
        Genre expectedGenre = new Genre("Test genre", List.of());
        expectedGenre = genreDao.save(expectedGenre);
        Optional<Genre> optionalActualGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should update one genre")
    @Test
    void shouldUpdateOneGenre() {
        Genre expectedGenre = new Genre(5L, "Edited genre", null);
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
        boolean actualValue = genreDao.existsById(6);
        assertThat(actualValue).isFalse();
    }

    @DisplayName("Should find one genre")
    @Test
    void shouldFindOneGenre() {
        Author expectedAuthor = new Author(1, "Александр Сергеевич Пушкин", null);
        Book expectedBook = new Book(1, "Руслан и Людмила", expectedAuthor, null);
        Genre expectedGenre = new Genre(1, "Поэма", List.of(expectedBook));
        Optional<Genre> optionalActualGenre = genreDao.findById(1);
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }

    @DisplayName("Should find all genres")
    @Test
    void shouldFindAllGenres() {
        Genre expectedGenre1 = new Genre(1, "Поэма", null);
        Genre expectedGenre2 = new Genre(2, "Роман", null);
        Genre expectedGenre3 = new Genre(3, "Фэнтези", null);
        Genre expectedGenre4 = new Genre(4, "Детектив", null);
        Genre expectedGenre5 = new Genre(5, "Ужасы", null);
        List<Genre> expectedGenres = List.of(expectedGenre1, expectedGenre2, expectedGenre3, expectedGenre4, expectedGenre5);
        List<Genre> actualGenres = genreDao.findAll();
        assertThat(expectedGenres).isEqualTo(actualGenres);
    }

    @DisplayName("Should delete one genre")
    @Test
    void shouldDeleteOneGenre() {
        Genre expectedGenre = new Genre("Test genre", List.of());
        expectedGenre = genreDao.save(expectedGenre);
        Optional<Genre> optionalGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalGenre).hasValue(expectedGenre);
        genreDao.deleteById(expectedGenre.getId());
        Optional<Genre> optionalDeletedGenre = genreDao.findById(expectedGenre.getId());
        assertThat(optionalDeletedGenre).isEmpty();
    }
}