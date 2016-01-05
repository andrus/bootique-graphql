package org.objectstyle.bootique.graphql.orm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.cayenne.map.EntityResolver;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLSchema.Builder;

public class DefaultSchemaTranslator implements SchemaTranslator {

	private ConcurrentMap<Class<?>, GraphQLOutputType> typeCache;

	public DefaultSchemaTranslator() {
		typeCache = new ConcurrentHashMap<>();
		typeCache.put(Boolean.class, Scalars.GraphQLBoolean);

		typeCache.put(String.class, Scalars.GraphQLString);

		typeCache.put(Integer.class, Scalars.GraphQLInt);
		typeCache.put(Integer.TYPE, Scalars.GraphQLInt);
		typeCache.put(Short.class, Scalars.GraphQLInt);
		typeCache.put(Short.TYPE, Scalars.GraphQLInt);
		typeCache.put(Byte.class, Scalars.GraphQLInt);
		typeCache.put(Byte.TYPE, Scalars.GraphQLInt);

		typeCache.put(Long.class, Scalars.GraphQLLong);
		typeCache.put(Long.TYPE, Scalars.GraphQLLong);
		typeCache.put(BigInteger.class, Scalars.GraphQLLong);

		typeCache.put(Float.class, Scalars.GraphQLFloat);
		typeCache.put(Float.TYPE, Scalars.GraphQLFloat);
		typeCache.put(Double.class, Scalars.GraphQLFloat);
		typeCache.put(Double.TYPE, Scalars.GraphQLFloat);
		typeCache.put(BigDecimal.class, Scalars.GraphQLFloat);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public GraphQLSchema toGraphQL(EntityResolver cayenneSchema) {
		Builder builder = GraphQLSchema.newSchema();

		// had to strip generics, as graphql-java is not using collection
		// bounds...
		Set entityTypes = entityTypes(cayenneSchema);
		GraphQLObjectType rootQueryType = queryType(entityTypes);

		return builder.query(rootQueryType).build(entityTypes);
	}

	protected GraphQLObjectType queryType(Set<GraphQLObjectType> entityTypes) {
		GraphQLObjectType.Builder typeBuilder = GraphQLObjectType.newObject().name("root");

		// naive... root type should be a user-visible builder

		// ... create select operations for all entities

		entityTypes.forEach(t -> {

			String fetchAllName = "all" + t.getName() + "s";
			GraphQLList type = new GraphQLList(t);

			GraphQLFieldDefinition f = GraphQLFieldDefinition.newFieldDefinition().name(fetchAllName).type(type)
					.build();

			typeBuilder.field(f);
		});

		// ... create search by ID operations for all entities
		entityTypes.forEach(t -> {

			String fetchByIdName = uncap(t.getName());

			GraphQLArgument idArg = GraphQLArgument.newArgument().description("entity ID").name("id")
					.type(Scalars.GraphQLID).build();

			GraphQLFieldDefinition f = GraphQLFieldDefinition.newFieldDefinition().name(fetchByIdName).type(t)
					.argument(idArg).build();

			typeBuilder.field(f);
		});

		return typeBuilder.build();
	}

	protected String uncap(String entityName) {
		return Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1);
	}

	protected Set<GraphQLObjectType> entityTypes(EntityResolver cayenneSchema) {

		Set<GraphQLObjectType> types = new HashSet<>();
		cayenneSchema.getObjEntities().forEach(oe -> {

			GraphQLObjectType.Builder typeBuilder = GraphQLObjectType.newObject().name(oe.getName());

			oe.getAttributes().forEach(oa -> {
				GraphQLOutputType type = mapAttributeType(oa.getJavaClass());
				GraphQLFieldDefinition f = GraphQLFieldDefinition.newFieldDefinition().name(oa.getName()).type(type)
						.build();
				typeBuilder.field(f);
			});

			// TODO: relationships

			types.add(typeBuilder.build());
		});

		return types;
	}

	protected GraphQLOutputType mapAttributeType(Class<?> javaType) {

		return typeCache.computeIfAbsent(javaType, jt -> {
			// TODO: weak
			return Scalars.GraphQLString;
		});
	}
}
