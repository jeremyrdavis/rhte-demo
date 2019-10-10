package com.redhat.techexchange.whosaidit.game;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class RefereeTest {

  @Inject
  Referee referee;

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }
  WireMockServer twitterWireMockServer;
  WireMockServer apiGatewayWireMockServer;

  @BeforeEach
  public void setUpWiremock() {
    twitterWireMockServer = new WireMockServer(8090);
    twitterWireMockServer.start();
    WireMock.configureFor("localhost", twitterWireMockServer.port());
    System.out.println("Twitter WireMock configured");

    apiGatewayWireMockServer = new WireMockServer(8098);
    apiGatewayWireMockServer.start();
    WireMock.configureFor("localhost", apiGatewayWireMockServer.port());
    System.out.println("ApiGateway WireMock configured");

    setupStubs();
  }

  private void setupStubs() {
    twitterWireMockServer
      .stubFor(post(urlEqualTo("/status"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));

    apiGatewayWireMockServer
      .stubFor(post(urlEqualTo("/events"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));
  }


  @Test
  public void testCreateGame() {

    Game game = referee.createGame();
    assertNotNull(game);
    assertNotNull(game.id);
    assertNotNull(game.rounds);
    assertEquals(4, game.rounds.size());
    for (Round round : game.rounds.values()) {
      assertEquals(4, round.quotes.size());
      System.out.println(round);
    }
  }

  @Test
  public void testStartRound() throws InterruptedException {

    Game game = referee.createGame();
    game = referee.startRound();
    Thread.sleep(30000);
  }

}
