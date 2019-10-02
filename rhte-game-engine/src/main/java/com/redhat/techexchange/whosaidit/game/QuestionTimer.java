package com.redhat.techexchange.whosaidit.game;

import io.quarkus.scheduler.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionTimer {

  boolean active = false;

  private AtomicInteger COUNTER = new AtomicInteger();

  Map<Integer, Quote> quotes = new HashMap<>();

  public QuestionTimer() {
  }

  public QuestionTimer(Map<Integer, Quote> quotes) {
    this.quotes = quotes;
  }

  @Scheduled(every="5s")
  public void increase() {

    if (active) {
      System.out.println(quotes.get(COUNTER.incrementAndGet()));
      if (COUNTER.get() == 4) {
        COUNTER.set(1);
        active = false;
      }
    }
  }


  public void activate() {
    active = true;
  }

  public void deactivate() {
    active = false;
  }

  public void setQuotes(Map<Integer, Quote> quotes) {

    this.quotes.putAll(quotes);
  }
}
