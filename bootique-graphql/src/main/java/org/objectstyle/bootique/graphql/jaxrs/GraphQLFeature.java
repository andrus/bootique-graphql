package org.objectstyle.bootique.graphql.jaxrs;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.objectstyle.bootique.graphql.jaxrs.provider.BQGraphQLRestExceptionMapper;
import org.objectstyle.bootique.graphql.jaxrs.provider.ExecutionResultWriter;
import org.objectstyle.bootique.graphql.jaxrs.provider.GraphiQLRestQueryReader;

/**
 * Loads JAX-RS extensions for GraphQL processing.
 */
public class GraphQLFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register(BQGraphQLRestExceptionMapper.class);
		context.register(GraphiQLRestQueryReader.class);
		context.register(ExecutionResultWriter.class);
		context.register(GraphQLResource.class);
		return true;
	}
}