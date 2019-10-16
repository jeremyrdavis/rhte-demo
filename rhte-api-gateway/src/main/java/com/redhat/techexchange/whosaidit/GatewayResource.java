package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;
import io.vertx.axle.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GatewayResource {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  GameTracker gameTracker;

  @Inject
  EventBus eventBus;

  @PostConstruct
  void setUp() {

  }

  @GET
  @Path("/events")
  public List<Event> getAllEvents() {
    return gameTracker.getAllEvents();
  }

  @GET
  @Path("/events/latest")
  public Response getLatestEvent() {
    Event event = new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("Quote #1", Quote.Author.Hamilton));
    return Response.ok(gameTracker.getLatestEvent()).build();
  }

  @POST
  @Path("/event/game/start")
  public Response startGame() {
    logger.debug("GatewayResource.startGame");
    gameTracker.startGame();
    return Response.ok().build();
  }

  @POST
  @Path("/event/quote")
  public Response nextQuote() {
    logger.debug("GatewayResource.nextQuote");
    eventBus.publish("nextQuote", null);
    return Response.ok().build();
  }

  @POST
  @Path("/event/round/start")
  public Response startRound() {
    logger.debug("GatewayResource.startRound");
    eventBus.publish("roundStart", null);
    return Response.ok().build();
  }

  @POST
  @Path("/event/round/end")
  public Response endRound() {
    logger.debug("GatewayResource.endRound");
    eventBus.publish("roundEnd", null);
    return Response.ok().build();
  }

  @GET
  @Path("/rounds/")
  public CompletionStage<String> startMockGame() {
    CompletableFuture<String> future = new CompletableFuture<>();
    eventBus.publish("roundStart", null);
    return future;
  }

/*
  @POST
  @Path("/event")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  public Response addEvent(String event) {
//    gameTracker.addEvent(event);
    JsonReader jsonReader = Json.createReader(new StringReader(event));
    JsonObject jobj = jsonReader.readObject();
    System.out.println(jobj);

    return Response.status(Response.Status.OK).build();
  }
*/

  @POST
  @Path("/event")
  public Response addEvent(NextQuoteEvent event) {
//    gameTracker.addEvent(event);
    System.out.println(event.toString());
    return Response.status(Response.Status.OK).build();
  }

  @POST
  @Path("/event/game/started")
  public Response addEvent(GameStartedEvent event) {
    gameTracker.addEvent(event);
    System.out.println(event.getEventType());
    return Response.status(Response.Status.OK).build();
  }

  @GET
  @Path("/event")
  public Response getEvent() {

//    return Response.ok(new NewQuoteEvent(new Quote("Test Quote", Quote.Author.Shakespeare))).build();
    return Response.ok(new GameStartedEvent()).build();
  }

  @GET
  @Path("/test/{eventType}")
  public Response getEventType(@PathParam("eventType") String eventType) {

    System.out.println(eventType);
    switch (eventType) {
      case "GameEndedEvent" :
        return Response.ok(new GameEndedEvent(EventType.GameEndedEvent, mockGame())).build();
      case "RoundEndedEvent":
        Round round = mockGame().getRounds().get(1);
        round.setWinner("@winner");
        return Response.ok(new RoundEndedEvent(EventType.RoundEndedEvent, round)).build();
      case "RoundStartedEvent":
        round = mockGame().getRounds().get(1);
        return Response.ok(new RoundEndedEvent(EventType.RoundStartedEvent, round)).build();
      case "GuessReceivedEvent" :
        return Response.ok(new GuessReceivedEvent(EventType.GuessReceivedEvent, "@player1")).build();
      case "NextQuoteEvent" :
        return Response.ok(new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("A Test", Quote.Author.Shakespeare))).build();
      case "GameStartedEvent" :
        return Response.ok(new GameStartedEvent(EventType.GameStartedEvent, mockGame())).build();
/*
        Round round = this.mockGame().getCurrentRound();
*/
      default:
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /*
    private Game mockGame{

      Game retVal = new Game();
      HashMap<Integer, Round> rounds = new HashMap<>(4);

/*
      for (int i = 1; i < 4; i++) {
        Round round = new Round();
        round.quotes = new HashMap<Integer, Quote>();
        for(int j = 0; j < 4; j++){
          if (j % 2 == 0) {
            round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Hamilton));
          } else {
            round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Shakespeare));
          }
        }
//      round.setWinner("@winningplayer#" + i);
//      rounds.put(i, round);
        retVal.addRound(round);
      }
      return retVal;
    }
*/
  }

  private Game mockGame() {

    Game game = new Game();
    HashMap<Integer, Round> rounds = new HashMap<>(4);
    for (int i = 1; i <=4; i++) {
      Round round = new Round();
      round.quotes = new HashMap<Integer, Quote>();
      for (int j = 0; j < 4; j++) {
        if (j % 2 == 0) {
          round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Hamilton));
        } else {
          round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Shakespeare));
        }

      }
      game.getRounds().put(i, round);
    }
    return game;
  }

}
