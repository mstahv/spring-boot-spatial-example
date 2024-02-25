package org.vaadin.example;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class DatabaseTestContainerConfiguration {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer postgis() {
        PostgreSQLContainer<?> postgis = new PostgreSQLContainer<>(
                DockerImageName.parse("postgis/postgis:16-3.4-alpine").asCompatibleSubstituteFor("postgres")
        );
        return postgis;
    }
}