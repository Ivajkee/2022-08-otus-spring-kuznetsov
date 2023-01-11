package ru.otus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;
    private List<CommentDto> comments;

    public BookDto(long id, String title, AuthorDto author, GenreDto genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public BookDto(String title, AuthorDto author, GenreDto genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}
