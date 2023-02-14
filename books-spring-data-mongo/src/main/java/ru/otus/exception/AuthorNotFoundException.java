package ru.otus.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String text) {
        super("Author " + text + " not found!");
    }
}
