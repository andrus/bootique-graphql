package org.objectstyle.bootique.graphql.json;

import java.io.OutputStream;

public interface JsonWriter {

	void write(OutputStream out, JacksonWriterDelegate delegate);
}
