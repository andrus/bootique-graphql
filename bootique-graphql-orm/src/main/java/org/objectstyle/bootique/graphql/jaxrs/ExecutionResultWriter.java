package org.objectstyle.bootique.graphql.jaxrs;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import graphql.ExecutionResult;

@Provider
@Singleton
public class ExecutionResultWriter implements MessageBodyWriter<ExecutionResult> {

	private JsonFactory jsonFactory;

	public ExecutionResultWriter() {

		ObjectMapper jsonMapper = new ObjectMapper();

		this.jsonFactory = jsonMapper.getFactory();

		// make sure mapper does not attempt closing streams it does not
		// manage... why is this even a default in jackson?
		jsonFactory.disable(Feature.AUTO_CLOSE_TARGET);

		// do not flush every time. why would we want to do that?
		// this is having a HUGE impact on performance
		jsonMapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
	}

	@Override
	public long getSize(ExecutionResult t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return ExecutionResult.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(ExecutionResult t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
					throws IOException, WebApplicationException {

		try (JsonGenerator generator = jsonFactory.createGenerator(entityStream, JsonEncoding.UTF8)) {
			generator.writeStartObject();

			// TODO: is it ok to rely on default serialization of data and
			// errors?

			if (t.getData() != null) {
				generator.writeObjectField("data", t.getData());
			}

			if (t.getErrors() != null && !t.getErrors().isEmpty()) {
				generator.writeObjectField("errors", t.getErrors());
			}

			generator.writeEndObject();
		}
	}
}
