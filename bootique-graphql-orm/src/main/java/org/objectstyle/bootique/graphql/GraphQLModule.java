package org.objectstyle.bootique.graphql;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.objectstyle.bootique.graphql.orm.DefaultSchemaTranslator;
import org.objectstyle.bootique.graphql.orm.SchemaTranslator;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nhl.bootique.ConfigModule;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

public class GraphQLModule extends ConfigModule {

	@Override
	public void configure(Binder binder) {
		binder.bind(SchemaTranslator.class).to(DefaultSchemaTranslator.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	public GraphQL createGraphQL(ServerRuntime cayenneRuntime, SchemaTranslator translator) {
		GraphQLSchema schema = translator.toGraphQL(cayenneRuntime.getChannel().getEntityResolver());
		return new GraphQL(schema);
	}
}
