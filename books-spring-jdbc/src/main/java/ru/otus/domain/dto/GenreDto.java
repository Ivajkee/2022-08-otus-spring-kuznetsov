package ru.otus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private long id;
    private String name;
    private List<BookDto> books;

    public GenreDto(String name, List<BookDto> books) {
        this.name = name;
        this.books = books;
    }
}
