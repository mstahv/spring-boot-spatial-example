package org.vaadin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
