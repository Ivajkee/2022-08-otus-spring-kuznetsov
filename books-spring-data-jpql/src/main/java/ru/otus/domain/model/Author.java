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
@Table(name = "authors")
@NamedEntityGraph(name = "author-books-graph", attributeNodes = {
        @NamedAttributeNode("books")
})
public class Author {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    public Author(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public boolean addBook(Book book) {
        if (books.contains(book)) {
            return false;
        }
        return books.add(book);
    }

    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
