package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceTest {
    @Autowired
    private GenreService genreService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private MongoTemplate mongoTemplate;
    @MockBean
    private ConversionService conversionService;

    @DisplayName("Should return expected genres count")
    @Test
    void shouldReturnExpectedGenresCount() {
        long expectedCount = 3;
        when(genreRepository.count()).thenReturn(expectedCount);
        long actualCount = genreService.getCountOfGenres();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("Should save genre")
    @Test
    void shouldSaveGenre() {
        String id = "1";
        GenreDto genreDto = new GenreDto("New genre");
        Genre genre = new Genre(genreDto.getName());
        Genre savedGenre = new Genre(id, genre.getName());
        GenreDto expectedGenreDto = new GenreDto(id, savedGenre.getName());
        when(genreRepository.save(genre)).thenReturn(savedGenre);
        when(conversionService.convert(genreDto, Genre.class)).thenReturn(genre);
        when(conversionService.convert(savedGenre, GenreDto.class)).thenReturn(expectedGenreDto);
        GenreDto actualGenreDto = genreService.saveGenre(genreDto);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should update genre")
    @Test
    void shouldUpdateGenre() {
        String id = "1";
        GenreDto genreDto = new GenreDto(id, "Edited genre");
        Genre genre = new Genre(id, genreDto.getName());
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);
        when(conversionService.convert(genre, GenreDto.class)).thenReturn(expectedGenreDto);
        GenreDto actualGenreDto = genreService.updateGenre(genreDto);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should throw exception when try update not existing genre")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingGenre() {
        String id = "1";
        GenreDto genreDto = new GenreDto(id, "Edited genre");
        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> genreService.updateGenre(genreDto)).isInstanceOf(GenreNotFoundException.class);
    }

    @DisplayName("Should be exist genre")
    @Test
    void shouldBeExistGenre() {
        String id = "1";
        when(genreRepository.existsById(id)).thenReturn(true);
        boolean genreIsExist = genreService.existsGenreById(id);
        assertThat(genreIsExist).isTrue();
    }

    @DisplayName("Should be not exist genre")
    @Test
    void shouldBeNotExistGenre() {
        String id = "1";
        when(genreRepository.existsById(id)).thenReturn(false);
        boolean genreIsExist = genreService.existsGenreById(id);
        assertThat(genreIsExist).isFalse();
    }

    @DisplayName("Should find genre by id")
    @Test
    void shouldFindGenreById() {
        String id = "1";
        Genre genre = new Genre(id, "Test genre");
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        when(conversionService.convert(genre, GenreDto.class)).thenReturn(expectedGenreDto);
        GenreDto actualGenreDto = genreService.findGenreById(id);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should throw exception when try find not existing genre by id")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingGenreById() {
        String id = "1";
        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> genreService.findGenreById(id)).isInstanceOf(GenreNotFoundException.class);
    }

    @DisplayName("Should find genre by name")
    @Test
    void shouldFindGenreByName() {
        String id = "1";
        String name = "Test genre";
        Genre genre = new Genre(id, name);
        GenreDto expectedGenreDto = new GenreDto(id, name);
        when(genreRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(genre));
        when(conversionService.convert(genre, GenreDto.class)).thenReturn(expectedGenreDto);
        GenreDto actualGenreDto = genreService.findGenreByName(name);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should throw exception when try find not existing genre by name")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingGenreByName() {
        String name = "Test genre";
        when(genreRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> genreService.findGenreByName(name)).isInstanceOf(GenreNotFoundException.class);
    }

    @DisplayName("Should find all genres")
    @Test
    void shouldFindAllGenres() {
        Genre genre1 = new Genre("1", "Test genre 1");
        Genre genre2 = new Genre("2", "Test genre 2");
        Genre genre3 = new Genre("3", "Test genre 3");
        GenreDto genreDto1 = new GenreDto(genre1.getId(), genre1.getName());
        GenreDto genreDto2 = new GenreDto(genre2.getId(), genre2.getName());
        GenreDto genreDto3 = new GenreDto(genre3.getId(), genre3.getName());
        List<Genre> genres = List.of(genre1, genre2, genre3);
        List<GenreDto> expectedGenreDtoList = List.of(genreDto1, genreDto2, genreDto3);
        when(genreRepository.findAll()).thenReturn(genres);
        when(conversionService.convert(genre1, GenreDto.class)).thenReturn(genreDto1);
        when(conversionService.convert(genre2, GenreDto.class)).thenReturn(genreDto2);
        when(conversionService.convert(genre3, GenreDto.class)).thenReturn(genreDto3);
        List<GenreDto> actualGenreDtoList = genreService.findAllGenres();
        assertThat(actualGenreDtoList).isEqualTo(expectedGenreDtoList);
    }

    @DisplayName("Should delete genre")
    @Test
    void shouldDeleteGenre() {
        String id = "63de89ad5d80675a7e52ac75";
        genreService.deleteGenreById(id);
        verify(genreRepository).deleteById(id);
    }
}