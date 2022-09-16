package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.service.TestingService;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        TestingService testingService = applicationContext.getBean(TestingService.class);
        testingService.start();
    }
}