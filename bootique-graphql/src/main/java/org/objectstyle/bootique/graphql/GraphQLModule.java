package org.objectstyle.bootique.graphql;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.glassfish.jersey.server.ResourceConfig;
import org.objectstyle.bootique.graphql.jaxrs.GraphQLResource;
import org.objectstyle.bootique.graphql.orm.DefaultSchemaTranslator;
import org.objectstyle.bootique.graphql.orm.SchemaTranslator;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nhl.bootique.ConfigModule;
import com.nhl.bootique.config.ConfigurationFactory;
import com.nhl.bootique.jersey.JerseyBinder;
import com.nhl.bootique.jersey.JerseyServlet;
import com.nhl.bootique.jersey.JerseyServletFactory;
import com.nhl.bootique.jetty.MappedServlet;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

public class GraphQLModule extends ConfigModule {

	@Override
	public void configure(Binder binder) {
		binder.bind(SchemaTranslator.class).to(DefaultSchemaTranslator.class).in(Singleton.class);

		JerseyBinder.contributeTo(binder).features(fc -> {
			fc.register(GraphQLResource.class);
			return true;
		});
	}

	@Provides
	@Singleton
	public GraphQL createGraphQL(ServerRuntime cayenneRuntime, SchemaTranslator translator) {
		GraphQLSchema schema = translator.toGraphQL(cayenneRuntime.getChannel().getEntityResolver());
		return new GraphQL(schema);
	}

	/**
	 * Overrides JerseyModule JerseyServlet definition with the one mapped to
	 * "/graphql" path.
	 * 
	 * @param configFactory
	 *            Bootique configuration factory.
	 * @param config
	 *            Jersey config object.
	 * @return Jersey servlet as {@link MappedServlet}.
	 */
	@JerseyServlet
	@Provides
	@Singleton
	private MappedServlet createJerseyServlet(ConfigurationFactory configFactory, ResourceConfig config) {
		return configFactory.config(JerseyServletFactory.class, configPrefix).initServletPathIfNotSet("/graphql/*")
				.createJerseyServlet(config);
	}
}
