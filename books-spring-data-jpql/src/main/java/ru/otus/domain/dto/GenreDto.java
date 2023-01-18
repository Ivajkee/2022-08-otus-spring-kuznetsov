package ru.otus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private long id;
    private String name;

    public GenreDto(String name) {
        this.name = name;
    }
}
