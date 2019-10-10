package com.redhat.techexchange.whosaidit.game;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RefereeRESTIntegrationTest {

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }

  @Inject
  Referee referee;

  static WireMockServer twitterWireMockServer;
  static WireMockServer apiGatewayWireMockServer;

  @BeforeAll
  public static void setUpWireMock() {
    twitterWireMockServer = new WireMockServer(8090);
    twitterWireMockServer.start();
    WireMock.configureFor("localhost", twitterWireMockServer.port());
    System.out.println("Twitter WireMock configured");

    apiGatewayWireMockServer = new WireMockServer(8098);
    apiGatewayWireMockServer.start();
    WireMock.configureFor("localhost", apiGatewayWireMockServer.port());
    System.out.println("ApiGateway WireMock configured");

    twitterWireMockServer
      .stubFor(post(urlEqualTo("/status"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));

    apiGatewayWireMockServer
      .stubFor(post(urlEqualTo("/events"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));
  }

  @AfterAll
  public static void cleanUpWiremock() {
    twitterWireMockServer.stop();
    apiGatewayWireMockServer.stop();
  }



  @Test
  public void testOnNextQuote() {

    referee.onNextQuote(new Quote("test", Author.Hamilton));

    verify(postRequestedFor(urlMatching("/events"))
      .withRequestBody(equalToJson("{\"status\":\"Test Status\"}"))
      .withHeader("Content-Type", matching("application/json")));
  }

  @Test
  public void testGameStart() {

    Response response = given()
      .contentType(MediaType.APPLICATION_JSON)
      .when()
      .post("/game/start");

    assertEquals(HttpStatus.SC_CREATED, response.statusCode());
    assertEquals(4, response.jsonPath().getMap("rounds").values().size());

  }




}
