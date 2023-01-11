package otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import ru.otus.config.ConversionServiceConfig;
import ru.otus.converter.GenreDtoToGenreConverter;
import ru.otus.converter.GenreToGenreDtoConverter;
import ru.otus.domain.dto.GenreDto;
import ru.otus.domain.model.Genre;
import ru.otus.exception.GenreNotFoundException;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;
import ru.otus.service.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GenreServiceImpl.class, ConversionServiceConfig.class, GenreDtoToGenreConverter.class,
        GenreToGenreDtoConverter.class})
class GenreServiceTest {
    @Autowired
    private GenreService genreService;
    @Autowired
    private ConversionService conversionService;
    @MockBean
    private GenreRepository genreRepository;

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
        long id = 1;
        GenreDto genreDto = new GenreDto("New genre");
        Genre genre = new Genre(genreDto.getName());
        Genre savedGenre = new Genre(id, genre.getName());
        when(genreRepository.save(genre)).thenReturn(savedGenre);
        GenreDto expectedGenreDto = new GenreDto(id, savedGenre.getName());
        GenreDto actualGenreDto = genreService.saveGenre(genreDto);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should update genre")
    @Test
    void shouldUpdateGenre() {
        long id = 1;
        GenreDto genreDto = new GenreDto(id, "Edited genre");
        Genre genre = new Genre(id, genreDto.getName());
        when(genreRepository.existsById(id)).thenReturn(true);
        when(genreRepository.update(genre)).thenReturn(genre);
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        GenreDto actualGenreDto = genreService.updateGenre(genreDto);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should throw exception when try update not existing genre")
    @Test
    void shouldThrowExceptionWhenTryUpdateNotExistingGenre() {
        long id = 1;
        GenreDto genreDto = new GenreDto(id, "Edited genre");
        when(genreRepository.existsById(id)).thenReturn(false);
        assertThatThrownBy(() -> genreService.updateGenre(genreDto)).isInstanceOf(GenreNotFoundException.class);
    }

    @DisplayName("Should be exist genre")
    @Test
    void shouldBeExistGenre() {
        long id = 1;
        when(genreRepository.existsById(id)).thenReturn(true);
        boolean genreIsExist = genreService.existsGenreById(id);
        assertThat(genreIsExist).isTrue();
    }

    @DisplayName("Should be not exist genre")
    @Test
    void shouldBeNotExistGenre() {
        long id = 1;
        when(genreRepository.existsById(id)).thenReturn(false);
        boolean genreIsExist = genreService.existsGenreById(id);
        assertThat(genreIsExist).isFalse();
    }

    @DisplayName("Should find genre")
    @Test
    void shouldFindGenre() {
        long id = 1;
        Genre genre = new Genre(id, "Test genre");
        when(genreRepository.findById(id)).thenReturn(Optional.of(genre));
        GenreDto expectedGenreDto = new GenreDto(id, genre.getName());
        GenreDto actualGenreDto = genreService.findGenreById(id);
        assertThat(actualGenreDto).isEqualTo(expectedGenreDto);
    }

    @DisplayName("Should throw exception when try find not existing genre")
    @Test
    void shouldThrowExceptionWhenTryFindNotExistingGenre() {
        long id = 1;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> genreService.findGenreById(id)).isInstanceOf(GenreNotFoundException.class);
    }

    @DisplayName("Should find all genres")
    @Test
    void shouldFindAllGenres() {
        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Genre genre3 = new Genre(3, "Test genre 3");
        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2, genre3));
        GenreDto genreDto1 = new GenreDto(genre1.getId(), genre1.getName());
        GenreDto genreDto2 = new GenreDto(genre2.getId(), genre2.getName());
        GenreDto genreDto3 = new GenreDto(genre3.getId(), genre3.getName());
        List<GenreDto> expectedGenreDtoList = List.of(genreDto1, genreDto2, genreDto3);
        List<GenreDto> actualGenreDtoList = genreService.findAllGenres();
        assertThat(actualGenreDtoList).isEqualTo(expectedGenreDtoList);
    }

    @DisplayName("Should delete genre")
    @Test
    void shouldDeleteGenre() {
        assertThatCode(() -> genreService.deleteGenreById(1)).doesNotThrowAnyException();
    }
}