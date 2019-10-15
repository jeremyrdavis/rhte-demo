package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;
import io.vertx.axle.core.eventbus.EventBus;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
  public Response addEvent(NewQuoteEvent event) {
//    gameTracker.addEvent(event);
    System.out.println(event.toString());
    return Response.status(Response.Status.OK).build();
  }

  @POST
  @Path("/event")
  public Response addEvent(GameStartedEvent event) {
    gameTracker.addEvent(event);
    System.out.println(event.toString());
    return Response.status(Response.Status.OK).build();
  }

  @GET
  @Path("/event")
  public Response getEvent() {

//    return Response.ok(new NewQuoteEvent(new Quote("Test Quote", Quote.Author.Shakespeare))).build();
    return Response.ok(new GameStartedEvent()).build();
  }


}
