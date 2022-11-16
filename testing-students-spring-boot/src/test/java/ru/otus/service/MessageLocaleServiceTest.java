package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MessageLocaleServiceTest {
    @Autowired
    private MessageLocaleService messageLocaleService;

    @DisplayName("Should load russian message from ru locale")
    @Test
    void shouldLoadRussianMessageFromRuLocale() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("ru"));
        String actualMessage = messageLocaleService.getMessage("testMessage");
        assertEquals("Тестовое сообщение", actualMessage);
    }

    @DisplayName("Should load english message from en locale")
    @Test
    void shouldLoadEnglishMessageFromEnLocale() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String actualMessage = messageLocaleService.getMessage("testMessage");
        assertEquals("Test message", actualMessage);
    }

    @DisplayName("Should load english message from en locale with param")
    @Test
    void shouldLoadEnglishMessageFromEnLocaleWithParam() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        String actualMessage = messageLocaleService.getMessage("testMessageParam", "world");
        assertEquals("Hello world!", actualMessage);
    }
}