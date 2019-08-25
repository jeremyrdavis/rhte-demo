package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
public class QuoteResourceTest {

  @Order(1)
  @Test
  public void testQuoteEndpoint() {
    given()
      .when().get("/quotes")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(0));
  }

  @Order(2)
  @Test
  public void testCreateQuote() {

    Map<String, String> payload = new HashMap<String, String>();
    payload.put("author", Authors.SHAKESPEARE.toString());
    payload.put("text", TestQuotes.ALL_THE_WORLDS_A_STAGE.text());

    given()
      .contentType(MediaType.APPLICATION_JSON)
      .body(payload)
      .when()
      .post("/quotes")
      .then()
      .assertThat()
      .statusCode(201)
      .body(
        "author", equalTo(Authors.SHAKESPEARE.toString()),
        "text", equalTo(TestQuotes.ALL_THE_WORLDS_A_STAGE.text()));
  }
}
