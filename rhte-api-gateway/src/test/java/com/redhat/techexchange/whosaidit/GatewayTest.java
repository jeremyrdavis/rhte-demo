package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GatewayTest {


  @Test
  public void testEventsEndpoint() {
    given()
      .when().get("/events")
      .then()
      .statusCode(200);
  }

}
