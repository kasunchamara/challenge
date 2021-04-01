# Otrium Challenge

Using technologies
1. Kotlin
2. Dagger
3. RxJava
4. MVP Architecture

How to update token?

1. go to src -> main -> res -> values -> conf.xml file
2. name="token">UPDATE THE TOKEN HERE<

== Apollo Client

**Attach to project**

[source,groovy,subs="attributes+"]
----

buildscript {
    ext.apollo_version = "2.5.4"
}
---------
dependencies{
    implementation "com.apollographql.apollo:apollo-runtime:$apollo_version"
    implementation "com.apollographql.apollo:apollo-rx3-support:$apollo_version"
    implementation "com.apollographql.apollo:apollo-http-cache:$apollo_version"
    implementation "com.apollographql.apollo:apollo-coroutines-support:$apollo_version"
    implementation "com.apollographql.apollo:apollo-normalized-cache-sqlite:$apollo_version"
}
----
