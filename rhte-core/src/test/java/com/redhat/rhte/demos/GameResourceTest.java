package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test the following endpoints:
 * /games
 * /games/{gameId}
 * /games/rounds/start/{gameId}
 * /games/rounds/stop/{gameId}
 * /games/start/{gameId}
 * /games/stop/{gameId}
 *
 */
@QuarkusTest
public class GameResourceTest {

  static final String URI = "/games";

  String buildUri() {

    return URI;
  }

  private String buildUri(String additionalUri) {

    return buildUri() + additionalUri;
  }


  /**
   * /games
   */
  @Test
  public void testGameEndpoint() {
    given()
      .when().get(buildUri())
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(0));
  }


}
