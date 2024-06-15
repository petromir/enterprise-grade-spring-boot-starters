package com.petromirdzhunev.spring.boot.conversion.xml;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Autoconfiguration for exposing {@link XmlConverter}.
 */
@AutoConfiguration
class SpringBootXmlAutoConfiguration {

    @Bean
    XmlConverter xmlConverter() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new ParameterNamesModule());
        xmlMapper.registerModule(new Jdk8Module());
        xmlMapper.registerModule(new JavaTimeModule());
        return new XmlConverter(xmlMapper);
    }
}
