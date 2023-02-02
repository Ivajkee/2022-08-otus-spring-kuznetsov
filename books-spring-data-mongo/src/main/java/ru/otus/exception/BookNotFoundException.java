package ru.otus.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String text) {
        super("Book " + text + " not found!");
    }
}
