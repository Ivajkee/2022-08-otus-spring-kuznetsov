package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.TestingService;

@RequiredArgsConstructor
@ShellComponent
public class TestingCommands {
    private final TestingService testingService;

    @ShellMethod(value = "Start testing", key = {"start", "start-testing"})
    public void start() {
        testingService.start();
    }
}
