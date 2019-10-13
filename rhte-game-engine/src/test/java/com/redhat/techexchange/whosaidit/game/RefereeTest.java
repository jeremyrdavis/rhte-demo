package com.redhat.techexchange.whosaidit.game;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.redhat.techexchange.whosaidit.game.domain.Game;
import com.redhat.techexchange.whosaidit.game.domain.Round;
import com.redhat.techexchange.whosaidit.game.infrastructure.Referee;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;

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

  static WireMockServer twitterWireMockServer;
  static WireMockServer apiGatewayWireMockServer;
  static WireMockServer historyServiceWireMockServer;

  @BeforeAll
  public static void setUpWiremock() {

    twitterWireMockServer = new WireMockServer(8093);
    twitterWireMockServer.start();
    WireMock.configureFor("localhost", twitterWireMockServer.port());
    System.out.println("Twitter WireMock configured");

    apiGatewayWireMockServer = new WireMockServer(8091);
    apiGatewayWireMockServer.start();
    WireMock.configureFor("localhost", apiGatewayWireMockServer.port());
    System.out.println("ApiGateway WireMock configured");

    historyServiceWireMockServer = new WireMockServer(8092);
    historyServiceWireMockServer.start();
    WireMock.configureFor("localhost", historyServiceWireMockServer.port());
    System.out.println("HistoryService WireMock configured");

    twitterWireMockServer
      .stubFor(post(urlEqualTo("/status"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));

    apiGatewayWireMockServer
      .stubFor(post(urlEqualTo("/events"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));

    historyServiceWireMockServer
      .stubFor(post(urlEqualTo("/api/events"))
        .willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withStatus(200)));
  }

  @AfterAll
  public static void cleanUpWiremock() {
    twitterWireMockServer.stop();
    apiGatewayWireMockServer.stop();
    historyServiceWireMockServer.stop();
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
    verify(postRequestedFor(urlEqualTo("/api/events"))
      .withHeader("Content-Type", equalTo("application/json"))
    );
  }

}
