package org.objectstyle.bootique.graphiql;

import java.net.URL;
import java.util.Objects;

public class GraphiQLServlet extends ResourceServlet {

	private static final long serialVersionUID = 7972600981940595010L;

	static URL baseUrl() {
		URL baseUrl = GraphiQLServlet.class.getResource("");
		return Objects.requireNonNull(baseUrl);
	}

	public GraphiQLServlet() {
		super(baseUrl());
	}
}
