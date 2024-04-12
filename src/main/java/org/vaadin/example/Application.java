package org.vaadin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vaadin.addons.maplibre.MapLibreBaseMapProvider;

/**
 * This would be the main app for deployment artifact.
 * For deployment, you need to configure DB.
 * For local testing & development, use TestApp that
 * launches PostGIS using TestContainers.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Configure default base map
    @Bean MapLibreBaseMapProvider baseMapProvider() {
        // NOTE, Create your own API key in maptiler! This is registered to work on localhost for the demo only
        return () -> "https://api.maptiler.com/maps/streets/style.json?key=G5n7stvZjomhyaVYP0qU";
    }

}
