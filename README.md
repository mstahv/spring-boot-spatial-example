# A Spring Boot example editing spatial data in relational database

![Alt text](./screenshot.png?raw=true "Screenshot")

This is a small example app that shows how one can use:

 * [Spring Boot](http://projects.spring.io/spring-boot/) and [Spring Data](http://projects.spring.io/spring-data/)
 * Latest [Hibernate](http://hibernate.org/orm/) with spatial features. At the application API, only standard JPA stuff (and Spring Data) is used.
 * ~~The example also uses [QueryDSL](http://www.querydsl.com) spatial query as an example. QueryDSL contain excellent support for spatial types.~~ QueryDSL example replaced with plain JPQL(with Hibernate spatial extensions) as the latest version is not compatible with latest JTS/Hibernate. See https://github.com/querydsl/querydsl/issues/2404. If you want to see the exaple of QueryDSL usage in this setup, check out a bit older version of the example.
 * Relational database, like GeoDB/H2 (default) or MySQL, which supports basic spatial types. If you want to run this example on MySQL, prepare a "spatialdemo" database and change DB credentials in src/main/resources/application.properties. It should be rather easy to switch to another DB supported by Hibernate as well. Geometry types in DB are a bit non-standard, that is something you might need to adjust a bit.
 * [Vaadin](https://vaadin.com/framework) and V-Leaflet add-on to build the UI layer. V-Leaflet is a Vaadin wrapper for [Leaflet](http://leafletjs.com), the awesome slippy map library. With V-Leaflet we use the [Editable extension](https://vaadin.com/directory#!addon/v-leaflet-editable) and its Vaadin Field implementations which make it dead simple to edit [JTS](http://tsusiatsoftware.net/jts/main.html) data types directly from the JPA entities. 
 * As base layer for maps, OpenStreetMap layer is used, but naturally any common background map can be used.

...to build a full-stack web app handling spatial data efficiently.

As the data is in an optimized form in the DB, it is possible to create efficient queries to the backend and e.g. only show features relevant to the current viewport of the map visualizing features or what ever you can with MySQL's spatial queries.

Enjoy!
