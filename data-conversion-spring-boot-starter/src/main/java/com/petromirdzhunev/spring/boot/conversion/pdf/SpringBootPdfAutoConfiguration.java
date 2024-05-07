package com.petromirdzhunev.spring.boot.conversion.pdf;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.petromirdzhunev.spring.boot.conversion.json.JsonConverter;

/**
 * Autoconfiguration for exposing {@link PdfConverter}.
 */
@AutoConfiguration
public class SpringBootPdfAutoConfiguration {

    @Bean
    PdfConverter pdfConverter() {
        return new PdfConverter();
    }
}
