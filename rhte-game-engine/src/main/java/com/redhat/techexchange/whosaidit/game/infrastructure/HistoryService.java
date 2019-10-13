package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/events")
public interface HistoryService {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response sendEvent(Event event);

}
