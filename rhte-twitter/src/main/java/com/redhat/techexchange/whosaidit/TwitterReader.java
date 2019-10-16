package com.redhat.techexchange.whosaidit;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class TwitterReader {

  org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterReader.class);

  // Twitter4j
  Twitter twitter = TwitterFactory.getSingleton();

  LocalDate today = LocalDate.now();

  private boolean active;

  @Scheduled(every="30s")
  public void readTimeline() {

    if (active) {
      try {
        ResponseList<Status> userTimeline = twitter.getMentionsTimeline();
        userTimeline.forEach(s -> {
          LocalDate createdDate = Instant.ofEpochMilli(s.getCreatedAt().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
          if(createdDate.isEqual(today)){
            System.out.println(s);
          }
        });
      } catch (TwitterException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
