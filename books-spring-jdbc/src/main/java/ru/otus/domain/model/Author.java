package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private long id;
    private String fullName ;
    private List<Book> books;

    public Author(String fullName, List<Book> books) {
        this.fullName = fullName;
        this.books = books;
    }

    public boolean addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        } else {
            if (books.contains(book)) {
                return false;
            }
        }
        return books.add(book);
    }
}
