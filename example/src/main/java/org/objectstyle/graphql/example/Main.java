package org.objectstyle.graphql.example;

/**
 * GraphQL server runner with example schema.
 */
public class Main {

	public static void main(String[] args) {
		JerseyModule jersey = new JerseyModule().packageRoot(GraphQLResource.class).servletPath("/graphql/*");
		Bootique.app(args).module(jersey).module(GraphQLModule.class).autoLoadModules().run();
	}
}
