package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.BaseEvent;
import com.redhat.techexchange.whosaidit.domain.Event;
import com.redhat.techexchange.whosaidit.domain.NewQuoteEvent;
import com.redhat.techexchange.whosaidit.domain.Quote;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

  @Inject
  GameTracker gameTracker;

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
  public Response getGame() {
    return Response.ok(gameTracker.getGame()).build();
  }

  @GET
  @Path("/rounds")
  public Response getAllRounds() {
    return Response.ok(gameTracker.getRounds()).build();
  }

  @POST
  @Path("/event")
  public Response addEvent(BaseEvent event) {
    gameTracker.addEvent(event);
    return Response.status(Response.Status.OK).build();
  }

  @POST
  @Path("/start")
  public Response startGame() {
    gameTracker.start();
    return Response.status(Response.Status.OK).build();
  }



}
