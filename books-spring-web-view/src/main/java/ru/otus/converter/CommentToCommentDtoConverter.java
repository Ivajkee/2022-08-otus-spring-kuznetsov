package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Comment;

@Component
public class CommentToCommentDtoConverter implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }
}
