package org.objectstyle.bootique.graphql.jaxrs;

import java.util.Collections;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.InvalidSyntaxError;
import graphql.language.SourceLocation;

/**
 * A REST resource exposing a GraphQL engine.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GraphQLResource {

	@Inject
	private GraphQL graphql;

	@POST
	public ExecutionResult post(String query) {
		return process(query);
	}

	// TODO: variables
	protected ExecutionResult process(String query) {

		if (query == null) {
			GraphQLError error = new InvalidSyntaxError(new SourceLocation(0, 0));
			return new ExecutionResultImpl(Collections.singletonList(error));
		}

		return graphql.execute(query);
	}
}
