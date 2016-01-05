package org.objectstyle.bootique.graphql.jaxrs.provider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.objectstyle.bootique.graphql.jaxrs.GraphiQLRestQuery;
import org.objectstyle.bootique.graphql.json.JsonReader;

import com.google.inject.Inject;

@Singleton
@Provider
public class GraphiQLRestQueryReader implements MessageBodyReader<GraphiQLRestQuery> {

	@Inject
	private JsonReader jsonParser;

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return GraphiQLRestQuery.class.isAssignableFrom(type);
	}

	@Override
	public GraphiQLRestQuery readFrom(Class<GraphiQLRestQuery> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
					throws IOException, WebApplicationException {

		return jsonParser.read(GraphiQLRestQuery.class, entityStream);
	}
}
