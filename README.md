# A Spring Boot example editing spatial data in relational database

![Alt text](./screenshot.png?raw=true "Screenshot")

## How To run?

Make sure you have Docker installed and running (needed to create test DB) and a modern Java IDE that supports at least JDK 21. (see older versions of the example if you are tied to some legacy versions).

Even if you don't want to run it, you probably want to first import the code to your favourite Java IDE (tested in IntelliJ last time) for easier exploring of the demo code. Then locate the TestApp class, and it's main method (src/main/test), run it! This will:

 * Use Docker to get a postgres with postgis extensions
 * Wire that to this Spring Boot app for development
 * Run the Vaadin UI in development mode

Alternatively, if you have Maven installed, run from CLI:

    mvn spring-boot:test-run

## What it showcases

This is a small example app that shows how one can use:

 * [Spring Boot](http://projects.spring.io/spring-boot/) and [Spring Data](https://spring.io/projects/spring-data)
 * Latest [Hibernate](http://hibernate.org/orm/) with spatial features. At the application API, only standard JPA stuff (and Spring Data) is used.
 * ~~The example also uses [QueryDSL](http://www.querydsl.com) spatial query as an example. QueryDSL contain excellent support for spatial types.~~ QueryDSL example replaced with plain JPQL(with Hibernate spatial extensions) as the latest version is not compatible with latest JTS/Hibernate. See https://github.com/querydsl/querydsl/issues/2404. If you want to see the example of QueryDSL usage in this setup, check out a bit older version of the example.
 * Relational database, like PostGis (default, Postgres + extentiosn), H2GIS or MySQL, which supports basic spatial types. The example automatically launches Docker image with PostGis for the demo using TestContainers, if run via TestApp class in src/test/java/org/vaadin/example. Not that Hibernate might need tiny adjustments for other databases.
 * [Vaadin](https://vaadin.com/) and [MapLibreGL }> add-on](https://vaadin.com/directory/component/maplibregl--add-on) to build the UI layer. MapLibre add-on is a Vaadin wrapper for [MapLibre GL JS](https://github.com/maplibre/maplibre-gl-js) slippy map widget and [mapbox-gl-draw](https://github.com/mapbox/mapbox-gl-draw). Its Vaadin field implementations which make it dead simple to edit [JTS](https://locationtech.github.io/jts/) data types directly from the JPA entities.
 * As base layer for maps, crisp vector format [OpenStreetMap](https://www.openstreetmap.org/) data via [MapTiler](https://www.maptiler.com) is used, but naturally any common background map can be used.

...to build a full-stack web app handling spatial data efficiently.

As the data is in an optimized form in the DB, it is possible to create efficient queries to the backend and e.g. only show features relevant to the current viewport of the map visualizing features or what ever you can with the spatial queries.

Enjoy!
