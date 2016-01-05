package org.objectstyle.bootique.graphiql;

import com.google.inject.Binder;
import com.nhl.bootique.ConfigModule;
import com.nhl.bootique.jetty.JettyBinder;

public class GraphiQLModule extends ConfigModule {

	@Override
	public void configure(Binder binder) {
		JettyBinder.contributeTo(binder).servlet(new GraphiQLServlet(), "/graphiql/*");
	}
}
