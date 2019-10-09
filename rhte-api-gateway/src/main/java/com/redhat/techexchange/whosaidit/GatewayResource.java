package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

  private LinkedHashSet<Event> events;

  @GET
  @Path("/events")
  public List<Event> getAllEvents() {
    this.events = new LinkedHashSet<>();
    this.events.add(new GameStartedEvent());
    this.events.add(new RoundStartedEvent());
    this.events.add(new NewQuoteEvent());
    this.events.add(new GuessReceivedEvent());
    this.events.add(new NewQuoteEvent());
    this.events.add(new NewQuoteEvent());
    this.events.add(new NewQuoteEvent());
    this.events.add(new RoundEndedEvent());
    this.events.add(new GameEndedEvent());
    return events.stream().collect(Collectors.toList());
  }

  @POST
  @Path("/event")
  public Response addEvent(Event event) {
    this.events.add(event);
    return Response.status(Response.Status.OK).build();
  }

}
