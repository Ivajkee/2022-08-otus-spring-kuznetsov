package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@Configuration
public class AppConfig {
    @Bean
    public ResourceConfig resourceConfig(@Value("${resource.config.url}") String url) {
        return new ResourceConfig(url);
    }
}
