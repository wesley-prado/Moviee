package com.codemages.Moviee;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@Testcontainers
class MovieeApplicationTests {
  @Container
  static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>( "postgres:latest"
  );

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add( "spring.datasource.url", postgreSQLContainer::getJdbcUrl );
    registry.add( "spring.datasource.username", postgreSQLContainer::getUsername );
    registry.add( "spring.datasource.password", postgreSQLContainer::getPassword );
    registry.add( "spring.jpa.hibernate.ddl-auto", () -> "update" );
  }

  @Test
  void contextLoads() {
    System.out.println( "Context loaded successfully!" );
  }

}
