package ru.otus.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class Book {
    @Id
    private String id;
    @Indexed
    @NotBlank
    @Field("title")
    private String title;
    @DBRef
    private Set<Author> authors = new HashSet<>();
    @DBRef
    private Set<Genre> genres = new HashSet<>();

    public Book(String title, Set<Author> authors, Set<Genre> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book(String title) {
        this.title = title;
    }

    public boolean addAuthor(Author author) {
        if (authors.contains(author)) {
            return false;
        }
        return authors.add(author);
    }

    public boolean deleteAuthor(Author author) {
        return authors.remove(author);
    }

    public boolean addGenre(Genre genre) {
        if (genres.contains(genre)) {
            return false;
        }
        return genres.add(genre);
    }

    public boolean deleteGenre(Genre genre) {
        return genres.remove(genre);
    }
}
