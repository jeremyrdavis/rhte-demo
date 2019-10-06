package com.redhat.techexchange.whosaidit.game;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RefereeResourceTest {

  @Inject
  Flyway flyway;

  String URI = "/game";

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void testStartingGame() {

    Response response = given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
      .post(URI);

    assertEquals(HttpStatus.SC_CREATED, response.statusCode());
    assertEquals(4, response.jsonPath().getList("rounds").size());

/*
    Response response = given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
      .post(URI + "/start")
      .then()
      .statusCode(HttpStatus.SC_CREATED)
      .contentType(ContentType.JSON)
      .extract();

      response.readEntity() jsonPath()
      .getList("rounds").size();

    assertEquals(4, rounds);
*/
  }
}
