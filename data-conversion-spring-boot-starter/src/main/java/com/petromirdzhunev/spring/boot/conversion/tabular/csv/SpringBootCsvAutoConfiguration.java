package com.petromirdzhunev.spring.boot.conversion.tabular.csv;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration for exposing {@link CsvConverter}.
 */
@AutoConfiguration
class SpringBootCsvAutoConfiguration {

    @Bean
    CsvConverter csvConverter() {
        return new CsvConverter();
    }
}
