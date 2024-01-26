package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

@RequiredArgsConstructor
@ConfigurationProperties("resource.config")
public class ResourceConfig {
    private static final String DEFAULT_CSV_URL = "static/questions/questions.csv";
    private final Map<String, String> urlMap;

    public String getUrl() {
        return urlMap.getOrDefault(LocaleContextHolder.getLocale().toString(),
                urlMap.getOrDefault("default", DEFAULT_CSV_URL));
    }
}
