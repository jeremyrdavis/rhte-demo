package com.redhat.techexchange.whosaidit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TwitterService {

  Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  Twitter twitter;

  public TwitterService() {

    twitter = TwitterFactory.getSingleton();
  }

  public Status updateStatus(String statusUpdate) {

    try {

      Status result = twitter.updateStatus(statusUpdate);
      LOGGER.info("Status updated at " + Date.from(Instant.now()) + " " + statusUpdate);
      return result;

    } catch (TwitterException e) {

      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public List<Status> getReplies() {

    try {
      ResponseList<Status> results = twitter.getMentionsTimeline();
      for(Status status : results){

        System.out.println(status.toString());
      }
      return results;
    } catch (Exception e) {

      System.out.println(e.getMessage());
    }

    return null;
  }
}
