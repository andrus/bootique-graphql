package org.objectstyle.bootique.graphql;

public class BQGraphQLException extends RuntimeException {

	private static final long serialVersionUID = 3803322745076345350L;

	public BQGraphQLException() {
		super();
	}

	public BQGraphQLException(String message, Throwable cause) {
		super(message, cause);
	}

	public BQGraphQLException(String message) {
		super(message);
	}

	public BQGraphQLException(Throwable cause) {
		super(cause);
	}
}
