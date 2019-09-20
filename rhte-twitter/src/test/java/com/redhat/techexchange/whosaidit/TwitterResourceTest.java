package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response.Status;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TwitterResourceTest {


  @Test
  public void testPublishStatusEndpoint() {

    JsonObject jsonObject = Json.createObjectBuilder()
      .add("status", "Demo Status Update " + UUID.randomUUID().toString())
      .build();

    System.out.println(jsonObject.toString());

    Response response = given()
      .contentType(ContentType.JSON)
      .body(jsonObject.toString())
      .when()
      .post("/status");

    assertEquals(HttpStatus.SC_CREATED, response.statusCode());
  }
}
