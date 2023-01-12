package ru.otus.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.dto.BookDto;
import ru.otus.domain.model.Book;

@Component
public class BookDtoToBookConverter implements Converter<BookDto, Book> {
    @Override
    public Book convert(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle());
    }
}
