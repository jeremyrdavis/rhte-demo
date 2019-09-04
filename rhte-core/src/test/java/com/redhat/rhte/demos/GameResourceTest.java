package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class GameResourceTest {

  static final String URI = "/games";
  static final String URI_VERSION = "/v1";

  String buildUri() {

    return URI + URI_VERSION;
  }

  @Test
  public void testGuessEndpoint() {
    given()
      .when().get(buildUri())
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(2));
  }

}
