package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class QuoteResourceTest {

  @Test
  public void testQuoteEndpoint() {
    given()
      .when().get("/quotes")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(0));
  }
}
