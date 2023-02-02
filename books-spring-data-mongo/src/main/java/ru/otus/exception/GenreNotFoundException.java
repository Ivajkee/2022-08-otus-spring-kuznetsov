package ru.otus.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String text) {
        super("Genre " + text + " not found!");
    }
}
