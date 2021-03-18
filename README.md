# GraphQL-Playground Spring Framework Boot Starters

#### We are looking for contributors!

Are you interested in improving our documentation, working on the codebase, reviewing PRs? Join the team!

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

  - [WARNING: NoClassDefFoundError when using GraphQL Java Tools > 5.4.x](#warning-noclassdeffounderror-when-using-graphql-java-tools--54x)
    - [Using Gradle](#using-gradle)
    - [Using Maven](#using-maven)
- [Documentation](#documentation)
- [Requirements and Downloads](#requirements-and-downloads)
- [Enable GraphQL Playground](#enable-graphql-playground)
  - [Basic settings](#basic-settings)
  - [CDN](#cdn)
  - [Custom static resources](#custom-static-resources)
  - [Customizing GraphQL Playground](#customizing-graphql-playground)
  - [Tabs](#tabs)
- [Contributions](#contributions)
- [Licenses](#licenses)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## WARNING: NoClassDefFoundError when using GraphQL Java Tools > 5.4.x

If you're using `graphql-java-tools` in combination with Spring Boot 2.1.x or below then you need to
set the
`kotlin.version` in your Spring Boot project explicitly to version >= 1.3.70, because Spring Boot
Starter parent of that Spring Boot version overrides it with a 1.2.* version of Kotlin.
`graphql-java-tools` requires 1.3.* however because of its coroutine support. If you don't override
this version you will run into a `NoClassDefFoundError`.

Spring Boot team has indicated the Kotlin version will be upgraded to 1.3 in Spring Boot 2.2.

### Using Gradle

Set the Kotlin version in your `gradle.properties`

```
kotlin.version=1.3.70
```

### Using Maven

Set the Kotlin version in your `<properties>` section

```xml
<properties>
  <kotlin.version>1.3.70</kotlin.version>
</properties>
```

# Documentation

See our new [Documentation](https://www.graphql-java-kickstart.com/spring-boot/).

Repository contains:

* `playground-spring-boot-starter`to embed `GraphQL Playground` tool for schema introspection and
  query debugging (see [GraphQL Playground](https://github.com/prisma/graphql-playground))

# Requirements and Downloads

Requirements:

* Java 1.8
* Spring Framework Boot > 2.x.x (web)

Maven:

```xml
<!-- to embed Playground tool -->
<dependency>
  <groupId>com.graphql-java-kickstart</groupId>
  <artifactId>playground-spring-boot-starter</artifactId>
  <version>${graphql.spring.boot.starter.version}</version>
</dependency>

```

# Enable GraphQL Playground

GraphQL Playground becomes accessible at root `/playground` (or as configured
in `graphql.playground.mapping`)
if `playground-spring-boot-starter` is added as a dependency to a boot application.

It uses an embedded `GraphQL Playground React`, in accordance to
the [official guide](https://github.com/prisma/graphql-playground#as-html-page), using the 'minimum
HTML' approach.

Available Spring Boot configuration parameters (either `application.yml`
or `application.properties`):

```yaml
graphql.playground:
    mapping: /playground
    endpoint: /graphql
    subscriptionEndpoint: /subscriptions
    staticPath.base: my-playground-resources-folder
    enabled: true
    pageTitle: Playground
    cdn:
        enabled: false
        version: latest
    settings:
        editor.cursorShape: line
        editor.fontFamily: "'Source Code Pro', 'Consolas', 'Inconsolata', 'Droid Sans Mono', 'Monaco', monospace"
        editor.fontSize: 14
        editor.reuseHeaders: true
        editor.theme: dark
        general.betaUpdates: false
        prettier.printWidth: 80
        prettier.tabWidth: 2
        prettier.useTabs: false
        request.credentials: omit
        schema.polling.enable: true
        schema.polling.endpointFilter: "*localhost*"
        schema.polling.interval: 2000
        schema.disableComments: true
        tracing.hideTracingResponse: true
    headers:
        headerFor: AllTabs
    tabs:
        - name: Example Tab
          query: classpath:exampleQuery.graphql
          headers:
            SomeHeader: Some value
          variables: 
            SomeVariable: Some variable
          responses:
            - classpath:exampleResponse1.json
            - classpath:exampleResponse2.json
```

## Basic settings

`mapping`, `endpoint` and `subscriptionEndpoint` will default to `/playground`, `/graphql`
and `/subscriptions`, respectively. Note that these values may not be empty.

`enabled` defaults to `true`, and therefor Playground will be available by default if the dependency
is added to a Spring Boot Web Application project.

`pageTitle` defaults to `Playground`.

`headers` allows you to specify headers for the default tab. Note that if your are using Spring
Security and CSRF is enabled CSRF, the CSRF token will be automatically added to the headers. These
headers will also be added to all the tabs configured under the [Tabs](#tabs) section. If a header
is defined both in this 'global' header list and the header list of the individual tabs, the 'local'
version will be used for that tab.

## CDN

The currently bundled version is `1.7.20`, which is - as of writing this - the latest release
of `GraphQL Playground React`. The CDN option uses `jsDelivr` CDN, if enabled. By default, it will
load the latest available release. Available CDN versions can be found on the project's
[jsDelivr page](https://www.jsdelivr.com/package/npm/graphql-playground-react). The CDN option is
disabled by default.

## Custom static resources

You can also specify a custom local version of Playground by setting the base path for `Playground`
resources in the `staticPath.base` property. Under this directory, you have to provide the following
files:

* `static/css/index.css`
* `static/js/middleware.js`
* `favicon.png`
* `logo.png`

This is identical to the directory structure of the CDN under the `build` subfolder (where these
files can be found).

## Customizing GraphQL Playground

Further GraphQL Playground settings can be specified under the `settings` group, which are
documented in the official
[GraphQL Playground readme](https://github.com/prisma/graphql-playground#settings). Note that
enum-like values are validated against the available options, and your application will not start if
wrong settings are provided. Similarly there is some basic validation for integer values (they must
be valid positive integers).

## Tabs

Optionally, you can specify tabs that will be present when the user first opens GraphQL Playground.
You can configure the query, variables, headers and even supply sample responses. Note that `query`
, `variables` and `responses` are expected to be resources of the appropriate format (GraphQL
for `query`, JSON for `variables` and `responses`).

# Contributions

Contributions are welcome. Please respect
the [Code of Conduct](http://contributor-covenant.org/version/1/3/0/).

# Licenses

`graphql-spring-boot-starter`, `altair-spring-boot-starter`, `graphiql-spring-boot-starter`
and `voyager-spring-boot-starter` are licensed under the MIT License. See [LICENSE](LICENSE.md) for
details.

[graphql-java License](https://github.com/andimarek/graphql-java/blob/master/LICENSE.md)

[graphiql License](https://github.com/graphql/graphiql/blob/master/LICENSE)

[graphql-js License](https://github.com/graphql/graphql-js/blob/master/LICENSE)
