package org.objectstyle.graphql.example;

import com.nhl.bootique.Bootique;

/**
 * GraphQL server runner with example schema.
 */
public class Main {

	public static void main(String[] args) {
		Bootique.app(args).autoLoadModules().run();
	}
}
