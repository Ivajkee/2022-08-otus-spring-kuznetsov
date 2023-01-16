package ru.otus.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = {"books"})
@EqualsAndHashCode(exclude = {"books"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genres")
@NamedEntityGraph(name = "genre-books-graph", attributeNodes = {
        @NamedAttributeNode("books")
})
public class Genre {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

    public boolean addBook(Book book) {
        if (books.contains(book)) {
            return false;
        }
        return books.add(book);
    }
}
