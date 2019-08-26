package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
public class QuoteResourceTest {

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {

    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void testQuoteEndpoint() {
    given()
      .when().get("/quotes")
      .then()
      .statusCode(200)
      .contentType(MediaType.APPLICATION_JSON)
      .body("", hasSize(2));
  }

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
        // id should be 5 because we have inserted 2 Quotes and 2 Guesses
        "id", equalTo(5),
        "author", equalTo(Authors.SHAKESPEARE.toString()),
        "text", equalTo(TestQuotes.ALL_THE_WORLDS_A_STAGE.text()));
  }
}
