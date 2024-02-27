package org.vaadin.example;

import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vaadin.addons.maplibre.MapLibreBaseMapProvider;

/**
 * This would be the main app for deployment artifact.
 * For deployment, you need to configure DB.
 * For local testing & development, use TestApp that
 * launches MySQL with TestContainers.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    VaadinServiceInitListener configureDefaultMapBackground() {
        return event -> {
            event.getSource().getContext().setAttribute(MapLibreBaseMapProvider.class, () -> {
                return "https://api.maptiler.com/maps/streets/style.json?key=G5n7stvZjomhyaVYP0qU";
            });
        };
    }
}
