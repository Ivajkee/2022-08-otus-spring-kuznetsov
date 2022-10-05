package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("resource.config")
public class CsvLocaleConfig {
    private final Map<String, String> urlMap;

    @Bean
    public ResourceConfig resourceConfig() {
        return new ResourceConfig(urlMap.getOrDefault(LocaleContextHolder.getLocale().toString(), urlMap.get("default")));
    }
}
