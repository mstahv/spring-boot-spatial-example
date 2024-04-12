# A Spring Boot example editing spatial data in relational database

![Alt text](./screenshot.png?raw=true "Screenshot")

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
