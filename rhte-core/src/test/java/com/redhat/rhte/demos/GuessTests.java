package com.redhat.rhte.demos;

import com.redhat.rhte.demos.domain.Guess;
import com.redhat.rhte.demos.domain.Quote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessTests {


  @Test
  public void testCorrectGuess() {

    Quote quote = new Quote(Authors.SHAKESPEARE.toString(), "All the world's a stage");
    Guess guess = new Guess("@contestant", Authors.SHAKESPEARE.toString(), null, quote);

    assertTrue(guess.isCorrect());
  }

  @Test
  public void testIncorrectGuess() {

    Quote quote = new Quote("All the world's a stage", Authors.SHAKESPEARE.toString());
    Guess guess = new Guess("@contestant", Authors.SHAKESPEARE.toString(), null, quote);

    assertFalse(guess.isCorrect());
  }
}
