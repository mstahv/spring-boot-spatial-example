package org.vaadin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vaadin.addons.maplibre.BaseMapConfigurer;

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
    @Bean
    BaseMapConfigurer baseMapProvider() {

        return map -> {
            // NOTE, Create your own API key in maptiler! This is registered to work on localhost for the demo only
            map.initStyle("https://api.maptiler.com/maps/streets/style.json?key=G5n7stvZjomhyaVYP0qU");
        };
    }

}
