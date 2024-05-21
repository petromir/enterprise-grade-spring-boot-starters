package com.petromirdzhunev.spring.boot.web.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.petromirdzhunev.spring.boot.web.fixtures.TestSpringBootWebApplication;

@SpringBootTest(classes = TestSpringBootWebApplication.class)
@AutoConfigureMockMvc
class GenericHttpExceptionHandlerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void handleMethodArgumentNotValid() throws Exception {
		mockMvc.perform(get("/test/method-argument-not-valid"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("application/problem+json"))
		       .andExpect(content().json("""
				           {
                               "type":"about:blank",
                               "title":"Bad Request",
                               "status":400,
                               "instance":"/test/method-argument-not-valid",
                               "errors": [
                                 "'TestController.methodArgumentNotValid.param' must not be null"
                               ]
				           }
				           """, false))
		       .andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	void handleConstraintViolationException() throws Exception {
		mockMvc.perform(get("/test/constraint-violation"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("application/problem+json"))
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
		       .andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	void handleIllegalStateException() throws Exception {
		mockMvc.perform(get("/test/illegal-state"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("application/problem+json"))
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
		       .andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	void handleIllegalArgumentException() throws Exception {
		mockMvc.perform(get("/test/illegal-argument"))
		       .andExpect(status().isBadRequest())
		       .andExpect(content().contentType("application/problem+json"))
		       .andExpect(content().json("""
			            {
			            	"type":"about:blank",
			             	"title":"Bad Request",
			             	"status":400,
			       			"detail":"Illegal argument",
			             	"instance":"/test/illegal-argument"
			            }
			            """, false))
		       .andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	void handleUnknownException() throws Exception {
		mockMvc.perform(get("/test/unknown-exception"))
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().contentType("application/problem+json"))
		       .andExpect(content().json("""
				       {
				       		"type":"about:blank",
				       		"title":"Internal Server Error",
				       		"status":500,
							"detail":"Unknown exception",
				       		"instance":"/test/unknown-exception"
				       }
				       """, false))
		       .andExpect(jsonPath("$.timestamp").exists());
	}
}