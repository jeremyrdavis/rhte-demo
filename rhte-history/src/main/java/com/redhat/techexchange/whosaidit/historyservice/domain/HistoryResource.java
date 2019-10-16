package com.redhat.techexchange.whosaidit.historyservice.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryResource {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @GET
  public List<Event> getAllEvents() {
    return Event.listAll();
  }

  @POST
  @Transactional
  public Response addEvent(JsonObject jsonPayload) {
/*
    JsonReader jsonReader = Json.createReader(new StringReader("{\"name\":\"Falco\",\"age\":3,\"bitable\":false}"));
    JsonObject jobj = jsonReader.readObject();
    System.out.println(jobj);
*/
    logger.debug("Received jsonPayload:\n" + jsonPayload);
    GameEvent gameEvent = new GameEvent();
    String eventType = jsonPayload.getString("eventType");

    if (eventType.equals(EventType.NextQuoteEvent.title)) {
      logger.debug("NextQuoteEventMatch");
      gameEvent.eventType = EventType.NextQuoteEvent;
      gameEvent.entityType = EntityType.Quote;
      gameEvent.data = jsonPayload.getJsonObject("quote").toString();
    } else if (eventType.equals(EventType.GameStartedEvent.title)) {
      logger.debug("GameStartedEvent match");
      gameEvent.eventType = EventType.GameStartedEvent;
      gameEvent.entityType = EntityType.Game;
      gameEvent.data = jsonPayload.getJsonObject("game").toString();
    } else if (eventType.equals(EventType.RoundStartedEvent.title)) {
      logger.debug("RoundStartedEvent match");
      gameEvent.eventType = EventType.RoundStartedEvent;
      gameEvent.entityType = EntityType.Round;
      gameEvent.data = jsonPayload.getJsonObject("round").toString();
    } else if (eventType.equals(EventType.RoundEndedEvent.title)) {
      logger.debug("RoundEndedEvent match");
      gameEvent.eventType = EventType.RoundEndedEvent;
      gameEvent.entityType = EntityType.Round;
      gameEvent.data = jsonPayload.getJsonObject("round").toString();
    } else if (eventType.equals(EventType.GuessReceivedEvent.title)) {
      logger.debug("GuessReceivedEvent match");
      gameEvent.eventType = EventType.GuessReceivedEvent;
      gameEvent.entityType = EntityType.Guess;
      gameEvent.data = jsonPayload.getJsonObject("player").toString();
    } else if (eventType.equals(EventType.GameEndedEvent)) {
      gameEvent.eventType = EventType.RoundEndedEvent;
      gameEvent.entityType = EntityType.Round;
      gameEvent.data = jsonPayload.getJsonObject("round").toString();
    }
    gameEvent.persist();
    return Response.ok().build();
  }

}
