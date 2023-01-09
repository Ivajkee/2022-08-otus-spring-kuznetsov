package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.otus.config")
public class BooksSpringDataJpqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksSpringDataJpqlApplication.class, args);
    }
}