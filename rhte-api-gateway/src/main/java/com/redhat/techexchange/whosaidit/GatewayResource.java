package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

  private LinkedHashSet<BaseEvent> events;

  @GET
  @Path("/events")
  public List<Event> getAllEvents() {
    this.events = new LinkedHashSet<>();
    this.events.add(new GameStartedEvent());
    this.events.add(new RoundStartedEvent());
    this.events.add(new NewQuoteEvent(new Quote("Quote #1", Quote.Author.Hamilton)));
    this.events.add(new GuessReceivedEvent());
    this.events.add(new NewQuoteEvent(new Quote("Quote #2", Quote.Author.Swarzeneggar)));
    this.events.add(new NewQuoteEvent(new Quote("Quote #3", Quote.Author.Shakespeare)));
    this.events.add(new RoundEndedEvent());
    this.events.add(new GameEndedEvent());
    return events.stream().collect(Collectors.toList());
  }

  @POST
  @Path("/event")
  public Response addEvent(BaseEvent event) {
    this.events.add(event);
    return Response.status(Response.Status.OK).build();
  }

  @GET
  @Path("/events/latest")
  public Response getLatestEvent() {
    Event event = new NewQuoteEvent(new Quote("Quote #1", Quote.Author.Hamilton));
    return Response.ok(event).build();
  }

}
