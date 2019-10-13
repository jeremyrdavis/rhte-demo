package com.redhat.techexchange.whosaidit.historyservice;

import com.redhat.techexchange.whosaidit.historyservice.domain.Event;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoryResource {

  @GET
  public Response hello() {
    return Response.ok().build();
  }

  @POST
  @Path("/events")
  @Transactional
  public Response addEvent(Event event) {

    event.persist();
    return Response.ok().build();
  }
}
