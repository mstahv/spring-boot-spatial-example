package org.vaadin.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import(DatabaseTestContainerConfiguration.class)
@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
