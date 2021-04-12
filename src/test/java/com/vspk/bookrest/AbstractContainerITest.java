package com.vspk.bookrest;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
public class AbstractContainerITest {


    public static PostgreSQLContainer postgreSQLContainer ;

    static {
        postgreSQLContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2").asCompatibleSubstituteFor("postgres"))
                .withUsername("test")
                .withPassword("password")
                .withDatabaseName("bookrest");
        //withReuse(true); <- to reduce build time
        postgreSQLContainer.start();
    }

    @BeforeAll
    static void setUpAll() {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }


}
