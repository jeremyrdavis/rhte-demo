package com.redhat.techexchange.whosaidit.game;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

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
  public void testStartRound() throws InterruptedException {

    Game game = referee.createGame();
    game = referee.startRound();
    Thread.sleep(30000);
  }

}
