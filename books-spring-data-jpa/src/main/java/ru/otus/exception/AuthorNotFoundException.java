package ru.otus.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(long id) {
        super("Author with id " + id + " not found!");
    }

    public AuthorNotFoundException(String fullName) {
        super("Author with name " + fullName + " not found!");
    }
}
