package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import ru.otus.service.TestingService;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.otus.config")
public class TestingStudentsSpringBootApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TestingStudentsSpringBootApplication.class, args);
        TestingService testingService = applicationContext.getBean(TestingService.class);
        testingService.start();
    }
}
