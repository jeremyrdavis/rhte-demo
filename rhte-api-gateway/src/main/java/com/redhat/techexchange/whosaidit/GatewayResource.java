package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.Event;
import com.redhat.techexchange.whosaidit.domain.EventType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

  private HashSet<Event> events;

  @GET
  @Path("/events")
  public List<Event> getAllEvents() {
    this.events = new HashSet<>();
    this.events.add(new Event(EventType.GAME_STARTED));
    return events.stream().collect(Collectors.toList());
  }

  @POST
  @Path("/event")
  public Response addEvent(Event event) {
    this.events.add(event);
    return Response.status(Response.Status.OK).build();
  }

}
