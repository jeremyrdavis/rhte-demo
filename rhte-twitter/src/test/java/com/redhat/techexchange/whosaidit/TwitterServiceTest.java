package com.redhat.techexchange.whosaidit;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import twitter4j.Status;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    List<Status> results = this.twitterService.getReplies();
    assertNotNull(results);
  }


}
