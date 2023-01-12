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
    private List<AuthorDto> authors;
    private List<GenreDto> genres;
    private List<CommentDto> comments;

    public BookDto(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public BookDto(String title) {
        this.title = title;
    }
}
