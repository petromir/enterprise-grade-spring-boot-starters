package com.petromirdzhunev.spring.boot.conversion.json;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Autoconfiguration for exposing {@link JsonConverter}.
 */
@AutoConfiguration
class SpringBootJsonAutoConfiguration {

    @Bean
    JsonConverter jsonConverter(final ObjectMapper objectMapper) {
        return new JsonConverter(objectMapper.copy().registerModule(new JavaTimeModule()));
    }
}