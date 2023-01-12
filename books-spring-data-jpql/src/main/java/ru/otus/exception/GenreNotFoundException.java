package ru.otus.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(long id) {
        super("Genre with id " + id + " not found!");
    }
}
