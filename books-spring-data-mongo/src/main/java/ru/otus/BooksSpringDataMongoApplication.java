package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.otus.config")
public class BooksSpringDataMongoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksSpringDataMongoApplication.class, args);
    }
}
