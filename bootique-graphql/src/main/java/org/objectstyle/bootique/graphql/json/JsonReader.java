package org.objectstyle.bootique.graphql.json;

import java.io.InputStream;

public interface JsonReader {

	<T> T read(Class<T> type, InputStream jsonStream);
}
