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
  public void testCompleteRound() {

  }

}
