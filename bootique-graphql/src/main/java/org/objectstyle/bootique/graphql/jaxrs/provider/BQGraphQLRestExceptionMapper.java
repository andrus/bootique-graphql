package org.objectstyle.bootique.graphql.jaxrs.provider;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.objectstyle.bootique.graphql.jaxrs.BQGraphQLRestException;
import org.objectstyle.bootique.graphql.jaxrs.MessageResponse;
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

			if (exception.getCause() != null && exception.getCause().getMessage() != null) {
				log.append(" [cause: ").append(exception.getCause().getMessage()).append("]");
			}

			// include stack trace in debug mode...
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(log.toString(), exception);
			} else {
				LOGGER.info(log.toString());
			}
		}

		return Response.status(status).entity(new MessageResponse(message)).type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
}
