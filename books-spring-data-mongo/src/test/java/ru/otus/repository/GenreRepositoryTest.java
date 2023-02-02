package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.config.MongoDataInitializerTest;
import ru.otus.domain.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoDataInitializerTest.class})
@DataMongoTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Should find genre by name")
    @Test
    void shouldFindGenreByName() {
        Genre expectedGenre = new Genre("1", "Поэма");
        Optional<Genre> optionalActualGenre = genreRepository.findByNameIgnoreCase("поэма");
        assertThat(optionalActualGenre).hasValue(expectedGenre);
    }
}