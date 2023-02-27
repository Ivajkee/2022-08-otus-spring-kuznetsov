package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Should find genre by name")
    @Test
    void shouldFindGenreByName() {
        Genre expectedGenre = new Genre(1, "Поэма");
        Optional<Genre> optionalActualGenre = genreRepository.findByNameIgnoreCase("поэма");
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }
}