package com.redhat.techexchange.whosaidit.historyservice.domain;

import com.redhat.techexchange.whosaidit.historyservice.domain.Event;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
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
  public Response addEvent(JsonObject jsonPayload) {
/*
    JsonReader jsonReader = Json.createReader(new StringReader("{\"name\":\"Falco\",\"age\":3,\"bitable\":false}"));
    JsonObject jobj = jsonReader.readObject();
    System.out.println(jobj);
*/
    System.out.println(jsonPayload);
    GameEvent gameEvent = new GameEvent();
    String eventType = jsonPayload.getString("eventType");

    if(eventType.equals(EventType.NextQuoteEvent.title)){
      gameEvent.eventType = EventType.NextQuoteEvent;
      gameEvent.entityType = EntityType.Quote;
      gameEvent.data = jsonPayload.getJsonObject("quote").toString();
    }
    gameEvent.persist();
    return Response.ok().build();
  }

}
