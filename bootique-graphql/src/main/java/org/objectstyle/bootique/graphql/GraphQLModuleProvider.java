package org.objectstyle.bootique.graphql;

import com.google.inject.Module;
import com.nhl.bootique.BQModuleProvider;

public class GraphQLModuleProvider implements BQModuleProvider {

	@Override
	public Module module() {
		return new GraphQLModule();
	}
}
