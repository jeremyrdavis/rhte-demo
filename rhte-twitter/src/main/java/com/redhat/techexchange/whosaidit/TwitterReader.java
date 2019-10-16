package com.redhat.techexchange.whosaidit;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TwitterReader {

  org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterReader.class);

  // Twitter4j
  Twitter twitter = TwitterFactory.getSingleton();

  LocalDate today = LocalDate.now();

  private boolean active;

  @Scheduled(every="30s")
  public void readTimeline() {

    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8080/game/mentions");

    if (active) {
      try {
        ResponseList<Status> userTimeline = twitter.getMentionsTimeline();
        userTimeline.forEach(s -> {
          LocalDate createdDate = Instant.ofEpochMilli(s.getCreatedAt().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
          List<Status> mentions = new ArrayList<>();
          if(createdDate.isEqual(today)){
            System.out.println(s);
            mentions.add(s);
          }
          System.out.println("and now sending the mentions");
          mentions.forEach(m ->{
            System.out.println(m.getUser().getName());
            Response response = target.request().post(Entity.json(m.getUser().getName()));
//            gameService.reportMention();
            response.close();
          });
        });
        client.close();
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
