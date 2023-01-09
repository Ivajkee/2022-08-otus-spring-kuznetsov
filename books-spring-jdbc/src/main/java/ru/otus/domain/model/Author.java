package ru.otus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private long id;
    private String fullName;

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
