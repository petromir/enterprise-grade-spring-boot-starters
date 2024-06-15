package com.petromirdzhunev.spring.boot.conversion.pdf;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration for exposing {@link PdfConverter}.
 */
@AutoConfiguration
class SpringBootPdfAutoConfiguration {

    @Bean
    PdfConverter pdfConverter() {
        return new PdfConverter();
    }
}
