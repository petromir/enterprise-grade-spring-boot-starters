package com.petromirdzhunev.spring.boot.conversion.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petromirdzhunev.spring.boot.conversion.exception.DataConversionException;

@SpringBootTest(classes = TestingJsonWebApplication.class)
class JsonConverterTest {

	@Autowired
	private JsonConverter jsonConverter;

	@Test
	void objectToJson() {
		String expectedJson = """
            {"name":"Petromir"}
        """.strip();
		Person person = new Person("Petromir");

		String result = jsonConverter.objectToJson(person);

		assertThat(result).isEqualTo(expectedJson);
	}

	@Test
	void objectToJsonException() {
		assertThatThrownBy(() -> jsonConverter.objectToJson(new NonConvertable()))
				.isInstanceOf(DataConversionException.class)
				.hasMessage("Failed to convert Object to JSON [objectType=NonConvertable]");
	}

	@Test
	void jsonToObject() {
		String json = """
            {"name":"Petromir"}
        """;
		Person expectedPerson = new Person("Petromir");

		Person result = jsonConverter.jsonToObject(json, Person.class);

		assertThat(result).isEqualTo(expectedPerson);
	}

	@Test
	void jsonToObjectException() {
		String json = """
            {"name":"Petromir"
        """.strip();

		assertThatThrownBy(() -> jsonConverter.jsonToObject(json, Person.class))
				.isInstanceOf(DataConversionException.class)
				.hasMessage("Failed to convert JSON to Object [json={\"name\":\"Petromir\", objectType=Person]");
	}

	@Test
	void jsonToMap() {
		String json = """
            {"name":"Petromir"}
        """.strip();
		Map<String, String> expectedMap = Map.of("name", "Petromir");

		Map<String, String> result = jsonConverter.jsonToMap(json, String.class, String.class);

		assertThat(result).isEqualTo(expectedMap);
	}

	@Test
	void jsonToList() {
		String json = """
            ["Petromir", "Lilia"]
        """;
		List<String> expectedList = List.of("Petromir", "Lilia");

		List<String> result = jsonConverter.jsonToList(json, String.class);

		assertThat(result).isEqualTo(expectedList);
	}

	@Test
	void jsonToJsonNode() {
		String json = """
            {"name":"Petromir"}
        """.strip();

		JsonNode result = jsonConverter.jsonToJsonNode(json);

		assertThat(result).isNotNull();
		assertThat(result.get("name").asText()).isEqualTo("Petromir");
	}

	@Test
	void jsonToJsonNodeException() {
		String json = """
            {"name":"Petromir"
        """.strip();

		assertThatThrownBy(() -> jsonConverter.jsonToJsonNode(json))
				.isInstanceOf(DataConversionException.class)
				.hasMessage("Failed to convert JSON to JsonNode [json={\"name\":\"Petromir\"]");
	}

	@Test
	void delegateMapper() {
		ObjectMapper result = jsonConverter.delegateMapper();
		assertThat(result).isNotNull();
	}

	@Test
	void personObjectToJson() {
		String expectedJson = """
            {"name":"Petromir"}
        """.strip();
		Person person = new Person("Petromir");

		String result = jsonConverter.objectToJson(person);

		assertThat(result).isEqualTo(expectedJson);
	}

	@Test
	void jsonToPersonObject() {
		String json = """
            {"name":"Petromir"}
        """;

		Person expectedPerson = new Person("Petromir");

		Person result = jsonConverter.jsonToObject(json, Person.class);

		assertThat(result).isEqualTo(expectedPerson);
	}

	@Test
	void jsonToListOfPerson() {
		String json = """
            [{"name":"Petromir"},{"name":"Lilia"}]
        """;

		List<Person> expectedList = List.of(new Person("Petromir"), new Person("Lilia"));

		List<Person> result = jsonConverter.jsonToList(json, new TypeReference<>() {});

		assertThat(result).isEqualTo(expectedList);
	}

	record Person(String name) {}

	static class NonConvertable {}
}