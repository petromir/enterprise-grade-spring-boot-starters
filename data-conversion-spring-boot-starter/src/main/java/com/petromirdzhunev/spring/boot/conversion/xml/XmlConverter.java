package com.petromirdzhunev.spring.boot.conversion.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class XmlConverter {

    private static final String XML_HEADER = """
            <?xml version='1.0' encoding='UTF-8'?>
            """;

    private final XmlMapper xmlMapper;

    public <TYPE> String objectToXml(final TYPE object) {
        return objectToXml(object, false);
    }

    public <TYPE> String objectToXml(final TYPE object, boolean writeXmlDeclaration) {
        try {
            assertTypeIsAnnotated(object.getClass());
            final String xml = xmlMapper.writeValueAsString(object);
            return writeXmlDeclaration ? XML_HEADER + xml : xml;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Object to XML [objectType=%s]".formatted(object.getClass()
                    .getSimpleName()), e);
        }
    }

    public <TYPE> TYPE xmlToObject(final String xml, final Class<TYPE> type) {
        try {
            assertTypeIsAnnotated(type);
            return xmlMapper.readValue(xml, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert XML to Object [xml=%s, objectType=%s]".formatted(xml,
                    type.getSimpleName()), e);
        }
    }

	public XmlMapper delegateMapper() {
		return xmlMapper;
	}

    private void assertTypeIsAnnotated(final Class<?> type) {
        if (!type.isAnnotationPresent(JacksonXmlRootElement.class)) {
            throw new RuntimeException(
                    "The given type must be annotated with JacksonXmlRootElement [type=%s]".formatted(
                            type.getSimpleName()));
        }
    }
}
