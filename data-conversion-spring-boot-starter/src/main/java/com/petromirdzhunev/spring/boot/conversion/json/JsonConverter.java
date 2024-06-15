package com.petromirdzhunev.spring.boot.conversion.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.petromirdzhunev.spring.boot.conversion.exception.DataConversionException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class JsonConverter {

    private final ObjectMapper objectMapper;

    public <TYPE> String objectToJson(final TYPE object) {
        try {
            return objectMapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
	        throw new DataConversionException("Failed to convert Object to JSON [objectType=%s]".formatted(
			        object.getClass().getSimpleName()));
        }
    }

    public <TYPE> TYPE jsonToObject(final String json, final Class<TYPE> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new DataConversionException("Failed to convert JSON to Object [json=%s, objectType=%s]"
		            .formatted(json, type.getSimpleName()), e);
        }
    }

	public <TYPE> TYPE jsonToObject(final String json, final TypeReference<TYPE> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (JsonProcessingException e) {
			throw new DataConversionException("Failed to convert JSON to Type [json=%s, typeReference=%s]"
					.formatted(json, typeReference.getType()), e);
		}
	}

    public <KEY, VALUE> Map<KEY, VALUE> jsonToMap(final String json, final Class<KEY> keyType,
		    final Class<VALUE> valueType) {
        final MapLikeType mapType = TypeFactory.defaultInstance()
                .constructMapLikeType(Map.class, keyType, valueType);
        try {
            return objectMapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            throw new DataConversionException("Failed to convert JSON to Map [json=%s, mapType=%s]"
		            .formatted(json, mapType.toCanonical()), e);
        }
    }

    public <TYPE> List<TYPE> jsonToList(final String json, final Class<TYPE> listElementType) {

        final CollectionLikeType listType = TypeFactory.defaultInstance()
                .constructCollectionLikeType(List.class, listElementType);
        try {
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new DataConversionException("Failed to convert JSON to List [json=%s, listElementType=%s]"
		            .formatted(json, listType.toCanonical()), e);
        }
    }

    public <TYPE> List<TYPE> jsonToList(final String json, final TypeReference<TYPE> typeReference) {

        final CollectionLikeType listType = TypeFactory.defaultInstance()
                .constructCollectionLikeType(List.class, TypeFactory.defaultInstance()
                        .constructType(typeReference));
        try {
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new DataConversionException("Failed to convert JSON to List [json=%s, typeReference=%s]"
		            .formatted(json, listType.toCanonical()), e);
        }
    }

    public <TYPE, VALUE> TYPE mapToObject(final Map<String, VALUE> map, final Class<TYPE> objectType) {
        try {
            return objectMapper.convertValue(map, objectType);
        } catch (Exception e) {
            throw new DataConversionException("Failed to convert Map to Object [map=%s, objectType=%s]"
		            .formatted(map, objectType.getSimpleName()), e);
        }
    }

    public <TYPE> TYPE objectToObject(final Object object, final Class<TYPE> type) {
        try {
            return objectMapper.convertValue(object, type);
        } catch (IllegalArgumentException e) {
            throw new DataConversionException(
					"Failed to convert Object to Object [sourceObjectType=%s, targetObjectType=%s]"
							.formatted(object.getClass().getSimpleName(), type.getName()), e);
        }
    }

    public <TYPE> TYPE objectToObject(final Object object, final TypeReference<TYPE> typeReference) {
        try {
            return objectMapper.convertValue(object, typeReference);
        } catch (IllegalArgumentException e) {
            throw new DataConversionException(
					"Failed to convert Object to Object [sourceObjectType=%s, targetObjectType=%s]"
							.formatted(object.getClass().getSimpleName(), typeReference.getType()), e);
        }
    }

	public JsonNode jsonToJsonNode(final String json) {
		try {
			return objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			throw new DataConversionException("Failed to convert JSON to JsonNode [json=%s]".formatted(json));
		}
	}

    public ObjectMapper delegateMapper() {
        return objectMapper;
    }
}