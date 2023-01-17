package ru.otus.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book with id " + id + " not found!");
    }

    public BookNotFoundException(String title) {
        super("Book with name " + title + " not found!");
    }
}
