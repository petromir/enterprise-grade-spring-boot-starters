package com.petromirdzhunev.spring.boot.conversion.tabular.excel;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.petromirdzhunev.spring.boot.conversion.json.JsonConverter;

/**
 * Autoconfiguration for exposing {@link ExcelConverter}.
 */
@AutoConfiguration
public class SpringBootExcelAutoConfiguration {

    @Bean
    ExcelConverter excelConverter() {
        return new ExcelConverter();
    }
}
