package org.vaadin.example;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class MysqlTestContainerConfiguration {

    @Bean
    @ServiceConnection
    @RestartScope
    MySQLContainer mySQLContainer() {
        return new MySQLContainer<>("mysql:latest").withReuse(true);
    }
}