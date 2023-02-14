package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.config.MongoDataInitializerTest;
import ru.otus.domain.model.Author;
import ru.otus.domain.model.Book;
import ru.otus.domain.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoDataInitializerTest.class})
@DataMongoTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Should find book by title")
    @Test
    void shouldFindBookByTitle() {
        Author expectedAuthor = new Author("1", "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre("1", "Поэма");
        Book expectedBook = new Book("1", "Руслан и Людмила", Set.of(expectedAuthor), Set.of(expectedGenre));
        Optional<Book> optionalActualBook = bookRepository.findByTitleIgnoreCase("руслан и людмила");
        assertThat(optionalActualBook).hasValue(expectedBook);
    }

    @DisplayName("Should find book by author")
    @Test
    void shouldFindBookByAuthor() {
        Author expectedAuthor = new Author("1", "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre("1", "Поэма");
        Book expectedBook = new Book("1", "Руслан и Людмила", Set.of(expectedAuthor), Set.of(expectedGenre));
        List<Book> books = bookRepository.findAllByAuthors(expectedAuthor);
        assertThat(books).hasSize(1).contains(expectedBook);
    }

    @DisplayName("Should find book by genre")
    @Test
    void shouldFindBookByGenre() {
        Author expectedAuthor = new Author("1", "Александр Сергеевич Пушкин");
        Genre expectedGenre = new Genre("1", "Поэма");
        Book expectedBook = new Book("1", "Руслан и Людмила", Set.of(expectedAuthor), Set.of(expectedGenre));
        List<Book> books = bookRepository.findAllByGenres(expectedGenre);
        assertThat(books).hasSize(1).contains(expectedBook);
    }
}