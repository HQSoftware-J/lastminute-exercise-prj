package it.hqsolutions.lastminute.exercise.datatransform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
	private ObjectMapper mapper;

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

	// TODO Throw and catch right exception
	public <R> R jsonToObject(String json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public <R> R jsonToObject(File json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public <R> R jsonToObject(Reader json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public <R> R jsonToObject(InputStream json, Class<R> returnClass) {

		R ret = null;
		try {
			ret = mapper.readValue(json, returnClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public <T> T jsonToObjectList(TypeReference<T> type, String json) {
		T data = null;

		try {
			data = mapper.readValue(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public <R> List<R> jsonToObjectList(Class<R> returnClass, InputStream json) {

		List<R> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<R>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		return ret;
	}

	public String objectToJSON(Object obj) {
		String jsonObj = null;
		try {
			jsonObj = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj;
	}
}
