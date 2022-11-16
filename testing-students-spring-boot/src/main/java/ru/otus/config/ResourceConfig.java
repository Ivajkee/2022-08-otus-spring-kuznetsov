package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("resource.config")
public class ResourceConfig {
    private static final String DEFAULT_CSV_URL = "static/questions/questions.csv";
    private final Map<String, String> urlMap;

    //todo для проверки английской локали, в проде удалить!!!)
    //@PostConstruct
    private void setLocale() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    public String getUrl() {
        return urlMap.getOrDefault(LocaleContextHolder.getLocale().toString(),
                urlMap.getOrDefault("default", DEFAULT_CSV_URL));
    }
}
