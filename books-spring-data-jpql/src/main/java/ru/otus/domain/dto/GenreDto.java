package ru.otus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private long id;
    private String name;
    private Set<BookDto> books;

    public GenreDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto(String name) {
        this.name = name;
    }
}
