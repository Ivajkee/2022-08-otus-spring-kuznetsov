package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private long id;
    private String name;
    private List<Book> books;

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
