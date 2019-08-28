package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class GameTests {

  @Test
  public void testStartingGame() {

    Game game = new Game();
    game.start();
    assertTrue(game.active);
  }
}
