package com.redhat.techexchange.whosaidit.game;

import io.quarkus.scheduler.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionTimer {

  private AtomicLong COUNTER = new AtomicLong();

  @Scheduled(every="5s")
  public void increase() {
    System.out.println("+"+COUNTER.incrementAndGet() + " " + LocalDateTime.now());
  }

}
