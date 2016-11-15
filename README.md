# A Spring Boot example editing spatial data in relational database

![Alt text](./screenshot.png?raw=true "Screenshot")

This is a small example app that shows how one can use

 * [Spring Boot](http://projects.spring.io/spring-boot/) and [Spring Data](http://projects.spring.io/spring-data/)
 * Latest [Hibernate](http://hibernate.org/orm/) with spatial features. At the application API, only standard JPA stuff (and Spring Data) is used. The example uses more recent version than provided by Spring boms, to overcome some issues with spatial features.
 * Relational database, like MySQL, which supports basic spatial types. If you want to run this example, prepare a "spatialdemo" database and possibly change DB credentials in src/main/resources/application.properties. It should be rather easy to switch to another DB as well.
 * [Vaadin](https://vaadin.com/framework) and V-Leaflet add-on to build the UI layer. V-Leaflet is a Vaadin wrapper for [Leaflet](http://leafletjs.com), the awesome slippy map library. With V-Leaflet we use the [Editable extension](https://vaadin.com/directory#!addon/v-leaflet-editable) and its Vaadin Field implementations which make it dead simple to edit [JTS](http://tsusiatsoftware.net/jts/main.html) data types directly from the JPA entities. 
 * As baselayer for maps, OpenStreetMap layer is used, but naturally any common background map can be used.

...to build a full-stack web app handling spatial data efficiently.

As the data is in an optimised form in the DB, it is possible to create efficient queries to the backend and e.g. only show features relevant to the current viewport of the map visualising features or what ever you can with MySQLs spatial queries.

Enjoy!
