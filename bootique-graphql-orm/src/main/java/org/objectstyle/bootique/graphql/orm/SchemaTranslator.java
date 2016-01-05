package org.objectstyle.bootique.graphql.orm;

import org.apache.cayenne.map.EntityResolver;

import graphql.schema.GraphQLSchema;

public interface SchemaTranslator {

	GraphQLSchema toGraphQL(EntityResolver cayenneSchema);
}
