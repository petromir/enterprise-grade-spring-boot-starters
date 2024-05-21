package com.petromirdzhunev.spring.boot.web.fixtures;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/test")
@Validated
class TestController {

    @GetMapping("/method-argument-not-valid")
    void methodArgumentNotValid(@NotNull final String param) {
        // This method throw MethodArgumentNotValidException
    }

    @GetMapping("/constraint-violation")
    void constraintViolation(@Valid final TestRequestModel testRequestModel) {
	    // This method throw ConstraintViolationException
    }

    @GetMapping("/illegal-state")
    void illegalState() {
        throw new IllegalStateException("Illegal state");
    }

    @GetMapping("/illegal-argument")
    void illegalArgument() {
        throw new IllegalArgumentException("Illegal argument");
    }

    @GetMapping("/unknown-exception")
    void unknownException() {
        throw new RuntimeException("Unknown exception");
    }

	private record TestRequestModel(@NotNull String param) {}
}
