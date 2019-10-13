package com.redhat.techexchange.whosaidit.historyservice;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HelloResourceTest {

  @Test
  public void testGameCreatedEventEndpoint() {

    GameStartedEvent gameStartedEvent = new GameStartedEvent(Date.from(Instant.now()));
    Jsonb jsonb = JsonbBuilder.create();
    System.out.println(jsonb.toJson(gameStartedEvent));

    given()
      .contentType("application/json")
      .when()
      .body(jsonb.toJson(gameStartedEvent))
      .post("/api/events")
      .then()
      .statusCode(200);
  }

  @Test
  public void testRoundStartedEventEndpoint() {

    RoundStartedEvent roundStartedEvent = new RoundStartedEvent(Date.from(Instant.now()));
  }

}
