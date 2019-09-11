package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
   * /games/start/{gameId}
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
      .statusCode(HttpStatus.SC_ACCEPTED)
      .contentType(ContentType.JSON)
      .extract()
      .response()
      .jsonPath()
      .get("status")
      .equals("active");
  }


  /**
   * PUT
   * /games/stop/{gameId}
   */
  @Test
  public void testStoppingGame() {

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("id", 1)
      .add("name", "testGame")
      .add("status", "ended")
      .build();

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
//      .body(jsonObject.toString())
      .put(URI + "/stop/3")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .contentType(MediaType.APPLICATION_JSON)
      .extract()
      .response()
      .jsonPath()
      .get("status")
      .equals("ended");
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
      .statusCode(HttpStatus.SC_CREATED)
      .contentType(MediaType.APPLICATION_JSON)
      .extract()
      .response()
      .jsonPath()
      .get("name")
      .equals("testGame");
  }

  @Test
  public void testCreatingGameWithDuplicateNameThrowsError() {

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("name", "Game#1")
      .build();

    given()
      .contentType(ContentType.JSON)
      .body(jsonObject.toString())
      .when()
      .post(URI)
      .then()
      .statusCode(HttpStatus.SC_BAD_REQUEST)
      .contentType(MediaType.APPLICATION_JSON)
      .extract()
      .response()
      .jsonPath()
      .get("message")
      .equals("A game with name \'Game#1\' already exists");
  }


  /**
   * POST
   * /games/rounds/start/{gameId}
   */
  @Test
  public void testStartRound() {

    Response response = given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
      .post(URI + "/rounds/start/1");

    System.out.println(response.body().prettyPrint());

    assertEquals(HttpStatus.SC_ACCEPTED, response.statusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.contentType());
    assertEquals(Round.RoundStatus.ACTIVE.name, response.body().jsonPath().get("activeRound.status"));


  }

  /**
   * PUT
   * /games/rounds/stop/{gameId}
   */
  @Test
  public void testStopRound() {

    Response response = given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
//      .body(jsonObject.toString())
      .put(URI + "/rounds/stop/3/11");

    System.out.println(response.body().prettyPrint());

    assertEquals(HttpStatus.SC_ACCEPTED, response.statusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.contentType());
    assertNull(response.body().jsonPath().get("activeRound"));

/*
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .contentType(MediaType.APPLICATION_JSON)
      .body("status", equalTo("ended"));
*/
  }
}
