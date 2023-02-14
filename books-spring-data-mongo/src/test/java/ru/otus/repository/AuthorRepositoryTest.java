package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.config.MongoDataInitializerTest;
import ru.otus.domain.model.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({MongoDataInitializerTest.class})
@DataMongoTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Should find author by full name")
    @Test
    void shouldFindAuthorByFullName() {
        Author expectedAuthor = new Author("1", "Александр Сергеевич Пушкин");
        Optional<Author> optionalActualAuthor = authorRepository.findByFullNameIgnoreCase("александр сергеевич пушкин");
        assertThat(optionalActualAuthor).hasValue(expectedAuthor);
    }
}