package com.redhat.techexchange.whosaidit.historyservice.domain;

import com.redhat.techexchange.whosaidit.historyservice.domain.Event;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryResource {

  @GET
  public List<Event> getAllEvents() {
    return Event.listAll();
  }

  @POST
  @Transactional
  public Response addEvent(Event event) {
    System.out.println(event);
    event.persist();
    return Response.ok().build();
  }

}
