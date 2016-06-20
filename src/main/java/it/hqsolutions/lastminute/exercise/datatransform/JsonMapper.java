package it.hqsolutions.lastminute.exercise.datatransform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
	private ObjectMapper mapper;
	private static final Logger LOGGER = LoggerFactory.getLogger("HQSLogProfiler");

	public JsonMapper() {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public <R> R jsonToObject(String json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return ret;
	}

	public <R> R jsonToObject(File json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return ret;
	}

	public <R> R jsonToObject(Reader json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return ret;
	}

	public <R> R jsonToObject(InputStream json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return ret;
	}

	public <T> T jsonToObjectList(TypeReference<T> type, String json) {
		T data = null;

		try {
			data = mapper.readValue(json, type);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return data;
	}

	public <R> List<R> jsonToObjectList(Class<R> returnClass, InputStream json) {

		List<R> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<R>>() {
			});
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		return ret;
	}

	public String objectToJSON(Object obj) {
		String jsonObj = null;
		try {
			jsonObj = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
		}
		return jsonObj;
	}
}
