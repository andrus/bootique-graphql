package org.objectstyle.bootique.graphql.jaxrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.objectstyle.bootique.graphql.it.fixture.GraphQLJerseyTestOnDerby;

public class GraphQLResourceIT extends GraphQLJerseyTestOnDerby {

	@Test
	public void testSchemaQuery() {
		Response r = postGraphQL("{ __schema { types { name }}}");
		assertEquals(Status.OK.getStatusCode(), r.getStatus());

		String json = r.readEntity(String.class);
		assertTrue(json, json.startsWith("{\"data\":{\"__schema\":{\"types\":[{\"name\":"));
	}
	
	@Test
	public void testDataQuery() {
		
		insert("e1", "id, name", "1, 'a'");
		insert("e1", "id, name", "2, 'b'");
		
		Response r = postGraphQL("{ allE1s { name }}");
		assertEquals(Status.OK.getStatusCode(), r.getStatus());

		String json = r.readEntity(String.class);
		assertTrue(json, json.startsWith("{\"data\":{\"allE1s\":[{\"name\":"));
	}
}
