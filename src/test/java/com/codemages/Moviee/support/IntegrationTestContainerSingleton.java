package com.codemages.Moviee.support;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestContainerSingleton {
  private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(
    "postgres:latest" ).withEnv( "POSTGRES_INITDB_ARGS", "-d" );

  @BeforeAll
  static void beforeAll() {
    CONTAINER.start();
  }

  @AfterAll
  static void afterAll() {
    CONTAINER.stop();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add( "spring.datasource.url", CONTAINER::getJdbcUrl );
    registry.add( "spring.datasource.username", CONTAINER::getUsername );
    registry.add( "spring.datasource.password", CONTAINER::getPassword );
    registry.add( "spring.jpa.hibernate.ddl-auto", () -> "update" );
    registry.add( "moviee.security.remember-me-key", () -> "remember-me-key" );
  }
}
