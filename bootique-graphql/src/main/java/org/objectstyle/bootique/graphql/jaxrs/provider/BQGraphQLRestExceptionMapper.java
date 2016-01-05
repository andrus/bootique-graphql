package org.objectstyle.bootique.graphql.jaxrs.provider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.objectstyle.bootique.graphql.BQGraphQLRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Singleton
public class BQGraphQLRestExceptionMapper implements ExceptionMapper<BQGraphQLRestException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BQGraphQLRestExceptionMapper.class);

	@Override
	public Response toResponse(BQGraphQLRestException exception) {

		String message = exception.getMessage();
		Status status = exception.getStatus();

		if (LOGGER.isInfoEnabled()) {
			StringBuilder log = new StringBuilder();
			log.append(status.getStatusCode()).append(" ").append(status.getReasonPhrase());

			if (message != null) {
				log.append(" (").append(message).append(")");
			}

			// include stack trace in debug mode...
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(log.toString(), exception);
			} else {
				LOGGER.info(log.toString());
			}
		}

		Map<String, String> body = new HashMap<>();
		body.put("message", message);
		return Response.status(status).entity(body).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}
