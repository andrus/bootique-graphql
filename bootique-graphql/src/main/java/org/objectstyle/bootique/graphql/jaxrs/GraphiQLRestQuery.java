package org.objectstyle.bootique.graphql.jaxrs;

/**
 * A value holder object for GraphiQL queries coming via REST API.
 */
public class GraphiQLRestQuery {

	private String query;
	private String variables;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getVariables() {
		return variables;
	}
	
	public void setVariables(String variables) {
		this.variables = variables;
	}
}
