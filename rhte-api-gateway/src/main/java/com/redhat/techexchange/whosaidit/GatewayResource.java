package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.BaseEvent;
import com.redhat.techexchange.whosaidit.domain.Event;
import com.redhat.techexchange.whosaidit.domain.NewQuoteEvent;
import com.redhat.techexchange.whosaidit.domain.Quote;
import io.vertx.axle.core.eventbus.EventBus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

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
    Event event = new NewQuoteEvent(new Quote("Quote #1", Quote.Author.Hamilton));
    return Response.ok(gameTracker.getLatestEvent()).build();
  }

  @GET
  @Path("/game")
  public Response startGame() {
    System.out.println("GatewayResource.startGame");
    gameTracker.startGame();
    return Response.ok().build();
  }

  @GET
  @Path("/rounds/")
  public CompletionStage<String> startRounds() {
    CompletableFuture<String> future = new CompletableFuture<>();
    eventBus.publish("roundStart", null);
    return future;
  }

  @POST
  @Path("/event")
  public Response addEvent(BaseEvent event) {
    gameTracker.addEvent(event);
    return Response.status(Response.Status.OK).build();
  }

}
