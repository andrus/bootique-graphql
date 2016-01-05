A servlet embeddable in your GraphQL Java application, that provides [GraphiQL UI console](https://github.com/graphql/graphiql) for testing GraphQL queries against your app schema. If you have a [Bootique](https://github.com/nhl/bootique) app, including GraphiQL is as easy as declaring it in the pom:

```XML
<dependency>
	<groupId>org.objectstyle.bootique.graphql</groupId>
	<artifactId>bootique-graphiql</artifactId>
	<version>...</version>
</dependency>
```
Resources inlcuded in this module are assembled from Facebook GraphiQL sources per instructions 
[provided here](http://pcarion.com/2015/09/22/embed-graphql-server/).

