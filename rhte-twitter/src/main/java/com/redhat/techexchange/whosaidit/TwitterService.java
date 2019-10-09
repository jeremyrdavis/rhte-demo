package com.redhat.techexchange.whosaidit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TwitterService {

  Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  // Twitter4j
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

/*
  @Scheduled(every = "5s")
  public void pullReplies() {

    //if we don't have a sinceId yet
    if (sinceId == 0) {

      initialPull();
    }else{

      try {

        ResponseList<Status> results = twitter.getMentionsTimeline(new Paging(sinceId));
        for (Status status : results) {

          LOGGER.debug(status.getId() + " " + status.getText());
        }

        // update our sinceId
        Status lastStatus = results.get(results.size() - 1);
        this.sinceId = lastStatus.getId();
        LOGGER.debug("sinceId now set to " + sinceId);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }
  }
*/

  private void initialPull() {

    try {

      ResponseList<Status> results = twitter.getMentionsTimeline();
      for (Status status : results) {

        LOGGER.debug(status.getId() + " " + status.getText());
      }

      // update our sinceId
      Status lastStatus = results.get(results.size() - 1);
    } catch (Exception e) {

      System.out.println(e.getMessage());
    }
  }


  public List<Status> getReplies(long sinceId) {

    try {

      Paging paging = new Paging();
      paging.setSinceId(sinceId);

      ResponseList<Status> results = twitter.getMentionsTimeline(paging);
      for(Status status : results){

        System.out.println(status.toString());
      }
      return results;
    } catch (Exception e) {

      System.out.println(e.getMessage());
    }

    return new ArrayList<Status>(0);
  }

}
