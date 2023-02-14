package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.CommentDto;
import ru.otus.domain.model.Comment;

@Component
public class CommentDtoToCommentConverter implements Converter<CommentDto, Comment> {
    @Override
    public Comment convert(CommentDto commentDto) {
        return new Comment(commentDto.getText());
    }
}
