package com.redhat.techexchange.whosaidit.game;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class RefereeTest {

  @Inject
  Referee referee;


  @Test
  public void testCreateGame() {

    Game game = referee.createGame();
    assertNotNull(game);
    assertNotNull(game.id);
    assertNotNull(game.rounds);
    assertEquals(1, game.rounds.size());
  }

}
