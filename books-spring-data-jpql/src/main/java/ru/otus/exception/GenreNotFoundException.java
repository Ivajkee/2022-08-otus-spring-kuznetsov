package ru.otus.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(long id) {
        super("Genre with id " + id + " not found!");
    }

    public GenreNotFoundException(String name) {
        super("Genre with name " + name + " not found!");
    }
}
