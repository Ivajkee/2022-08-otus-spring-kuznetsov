package ru.otus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private long id;
    private String fullName;
    private List<BookDto> books;

    public AuthorDto(String fullName, List<BookDto> books) {
        this.fullName = fullName;
        this.books = books;
    }
}
