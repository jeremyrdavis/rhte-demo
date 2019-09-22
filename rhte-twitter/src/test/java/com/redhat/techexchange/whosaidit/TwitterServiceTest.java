package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import twitter4j.Status;

import javax.inject.Inject;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TwitterServiceTest {

  @Inject
  TwitterService twitterService;

  @Test
  public void updateStatusTest() {

    Status result = this.twitterService.updateStatus("Test status update " + UUID.randomUUID().toString());
    System.out.println(result);
    assertNotNull(result);
  }

  @Test
  public void testGetReplies() {

    List<Status> results = this.twitterService.getReplies(1139628612087418880L);
    assertNotNull(results);
    assertEquals(1, results.size());
  }


}
