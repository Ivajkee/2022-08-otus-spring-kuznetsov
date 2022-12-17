package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;

import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class ConverterConfig {
    private final Set<Converter<?, ?>> converters;
    private final ConfigurableConversionService conversionService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        converters.forEach(conversionService::addConverter);
    }
}
