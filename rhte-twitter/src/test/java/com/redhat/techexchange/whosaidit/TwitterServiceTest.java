package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import twitter4j.Status;

import javax.inject.Inject;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TwitterServiceTest {

  @Inject
  TwitterService twitterService;

  long initialRoundStatusId = 1175802228478742528L;

  @Test
  @Order(1)
  public void updateStatusTestNewRound() {

    Status result = this.twitterService.updateStatus("Test status update " + UUID.randomUUID().toString());
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  @Order(2)
  public void updateStatusTest() {

    Status result = this.twitterService.updateStatus("Test status update " + UUID.randomUUID().toString());
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  @Order(3)
  public void testGetReplies() {

    List<Status> results = this.twitterService.getReplies(initialRoundStatusId);
    assertNotNull(results);
    assertEquals(1, results.size());
  }


}
