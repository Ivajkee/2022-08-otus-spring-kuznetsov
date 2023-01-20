package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Set;

@Configuration
public class ConversionServiceConfig {
    @Bean
    public ConfigurableConversionService conversionService(Set<Converter<?, ?>> converters) {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        converters.forEach(defaultConversionService::addConverter);
        return defaultConversionService;
    }
}
