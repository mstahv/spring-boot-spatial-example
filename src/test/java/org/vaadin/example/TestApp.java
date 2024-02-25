package org.vaadin.example;

import org.springframework.boot.SpringApplication;

public class TestApp {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(DatabaseTestContainerConfiguration.class)
                .run(args);
    }

}