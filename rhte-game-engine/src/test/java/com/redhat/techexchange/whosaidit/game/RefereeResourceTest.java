package com.redhat.techexchange.whosaidit.game;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RefereeResourceTest {

  String URI = "/game";

  @Test
  public void testStartingGame() {

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
