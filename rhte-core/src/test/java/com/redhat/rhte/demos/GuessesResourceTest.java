package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class GuessesResourceTest {

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {

    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void testGuessEndpoint() {
    given()
      .when().get("/guesses")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(2));
  }
}
