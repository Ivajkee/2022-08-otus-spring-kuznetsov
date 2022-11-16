package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.TestingService;

import java.util.Locale;

@RequiredArgsConstructor
@ShellComponent
public class ApplicationCommands {
    private final TestingService testingService;

    @ShellMethod(value = "Start testing", key = {"start", "start-testing"})
    public void start() {
        testingService.start();
    }

    /**
     * Метод для изменения локали
     *
     * @param locale локаль, возможные значения - ru, en
     */
    @ShellMethod(value = "Change locale", key = {"loc", "locale"})
    public void changeLocale(@ShellOption String locale) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(locale));
    }
}
