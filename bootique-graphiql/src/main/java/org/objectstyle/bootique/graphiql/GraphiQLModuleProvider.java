package org.objectstyle.bootique.graphiql;

import com.google.inject.Module;
import com.nhl.bootique.BQModuleProvider;

public class GraphiQLModuleProvider implements BQModuleProvider {

	@Override
	public Module module() {
		return new GraphiQLModule();
	}
}
