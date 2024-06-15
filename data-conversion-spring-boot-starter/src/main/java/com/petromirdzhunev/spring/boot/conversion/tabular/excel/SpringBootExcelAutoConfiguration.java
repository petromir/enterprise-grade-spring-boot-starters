package com.petromirdzhunev.spring.boot.conversion.tabular.excel;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration for exposing {@link ExcelConverter}.
 */
@AutoConfiguration
class SpringBootExcelAutoConfiguration {

    @Bean
    ExcelConverter excelConverter() {
        return new ExcelConverter();
    }
}
