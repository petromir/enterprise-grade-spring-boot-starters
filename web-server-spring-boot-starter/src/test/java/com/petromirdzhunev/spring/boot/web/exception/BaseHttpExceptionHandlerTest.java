package com.petromirdzhunev.spring.boot.web.exception;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.petromirdzhunev.spring.boot.web.fixtures.TestSpringBootWebApplication;

@SpringBootTest(classes = TestSpringBootWebApplication.class)
@AutoConfigureMockMvc
class BaseHttpExceptionHandlerTest {

	// This is where the magic begins :)
	// Borrowed from https://www.myintervals.com/blog/2009/05/20/iso-8601-date-validation-that-doesnt-suck/
	public static final String TIMESTAMP_PATTERN = "^([+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24:?00)([.,]\\d+(?!:))?)?(\\17[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
	private static final String APPLICATION_PROBLEM_CONTENT_TYPE = "application/problem+json";
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void handleMethodArgumentNotValid() throws Exception {
		mockMvc.perform(get("/test/method-argument-not-valid/0"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
				           {
                               "type":"about:blank",
                               "title":"Bad Request",
                               "status":400,
                               "instance":"/test/method-argument-not-valid/0",
                               "errors": [
                                 "'TestController.methodArgumentNotValid.id' must be greater than or equal to 1"
                               ]
				           }
				           """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleConstraintViolationException() throws Exception {
		mockMvc.perform(post("/test/constraint-violation")
				       .contentType(MediaType.APPLICATION_JSON)
				       .content("""
						       {"param" : null}
						       """))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
				           {
                               "type":"about:blank",
                               "title":"Bad Request",
                               "status":400,
                               "instance":"/test/constraint-violation",
                               "errors": [
                					"'testRequestModel.param' must not be null"
                               ]
				           }
				           """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleIllegalStateException() throws Exception {
		mockMvc.perform(get("/test/illegal-state"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andDo(print())
		       .andExpect(content().json("""
			            {
			            	"type":"about:blank",
			             	"title":"Bad Request",
			             	"status":400,
			       			"detail":"Illegal state",
			             	"instance":"/test/illegal-state"
			            }
			            """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleIllegalArgumentException() throws Exception {
		mockMvc.perform(get("/test/illegal-argument"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
			            {
			            	"type":"about:blank",
			             	"title":"Bad Request",
			             	"status":400,
			       			"detail":"Illegal argument",
			             	"instance":"/test/illegal-argument"
			            }
			            """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleUnknownException() throws Exception {
		mockMvc.perform(get("/test/unknown-exception"))
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
				       {
				       		"type":"about:blank",
				       		"title":"Internal Server Error",
				       		"status":500,
							"detail":"Unknown exception",
				       		"instance":"/test/unknown-exception"
				       }
				       """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleTestException() throws Exception {
		mockMvc.perform(get("/test/test-exception"))
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
				       {
				       		"type":"about:blank",
				       		"title":"Internal Server Error",
				       		"status":500,
							"detail":"Test exception",
				       		"instance":"/test/test-exception"
				       }
				       """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}

	@Test
	void handleTestExceptionWithErrors() throws Exception {
		mockMvc.perform(get("/test/test-exception-with-errors"))
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().contentType(APPLICATION_PROBLEM_CONTENT_TYPE))
		       .andExpect(content().json("""
				       {
				       		"type":"about:blank",
				       		"title":"Internal Server Error",
				       		"status":500,
							"detail":"Test exception with errors",
				       		"instance":"/test/test-exception-with-errors",
                           	"errors": [
                    			"error message 1",
             					"error message 2"
                          	]
				       }
				       """, false))
		       .andExpect(jsonPath("$.timestamp").exists())
		       .andExpect(jsonPath("$.timestamp", matchesPattern(TIMESTAMP_PATTERN)));
	}
}