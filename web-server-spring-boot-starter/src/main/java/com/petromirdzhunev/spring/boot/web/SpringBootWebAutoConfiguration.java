package com.petromirdzhunev.spring.boot.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import com.petromirdzhunev.spring.boot.web.exception.BaseHttpExceptionHandler;

@AutoConfiguration
@ConditionalOnWebApplication
class SpringBootWebAutoConfiguration {

	@Bean
	BaseHttpExceptionHandler baseHttpExceptionHandler() {
		return new BaseHttpExceptionHandler();
	}
}
