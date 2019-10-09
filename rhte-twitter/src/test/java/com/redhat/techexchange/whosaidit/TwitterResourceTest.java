package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

  @Test
  public void testGetRepliesEndpoint() {

    Calendar calendar = new GregorianCalendar();
      calendar.set(Calendar.YEAR, 2019);
      calendar.set(Calendar.MONTH, 9);
      calendar.set(Calendar.DAY_OF_MONTH, 19);

    Date since = calendar.getTime();

    System.out.println(since.getTime());

    given()
      .when().get("/replies/" + since.getTime())
      .then()
      .statusCode(HttpStatus.SC_OK)
      .contentType(ContentType.JSON)
      .extract()
      .response()
      .jsonPath()
      .get("replies")
      .equals("Game #1");
  }
}
