package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
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
   * PUT
   * /games/start
   */
  @Test
  public void testStartingGame() {

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("id", 1)
      .add("name", "testGame")
      .add("status", "active")
      .build();

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
//      .body(jsonObject.toString())
      .put(URI + "/start/1")
      .then()
      .statusCode(Response.Status.ACCEPTED.getStatusCode())
      .contentType(MediaType.APPLICATION_JSON)
      .extract()
      .response()
      .jsonPath()
      .get("status")
      .equals("active");
  }

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
      .extract()
      .response()
      .jsonPath()
      .get("name")
      .equals("Game #1");
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

    System.out.println(jsonObject);

    given()
      .contentType(ContentType.JSON)
      .body(jsonObject.toString())
      .when()
      .post(URI)
      .then()
      .statusCode(Response.Status.CREATED.getStatusCode())
      .contentType(MediaType.APPLICATION_JSON)
      .extract()
      .response()
      .jsonPath()
      .get("name")
      .equals("testGame");
  }


}
