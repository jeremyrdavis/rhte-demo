package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

  /**
   * GET
   * /games
   */
  @Test
  public void testGetGame() {
    given()
      .when().get(URI + "/1")
      .then()
      .statusCode(302)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(1));
  }


  /**
   * POST
   * /games
   */
  @Test
  public void testCreateGame() {

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("name", "testGame")
      .build();

    given()
      .pathParam("game", jsonObject.toString() )
      .when().post(URI)
      .then()
      .statusCode(Response.Status.CREATED.getStatusCode())
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(1));
  }


}
