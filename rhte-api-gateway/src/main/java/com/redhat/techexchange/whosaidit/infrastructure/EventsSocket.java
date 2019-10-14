package com.redhat.techexchange.whosaidit.infrastructure;

import com.redhat.techexchange.whosaidit.domain.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/events")
@ApplicationScoped
public class EventsSocket {

  Map<String, Session> sessions = new ConcurrentHashMap<>();

  @OnOpen
  public void onOpen(Session session) {
    sessions.put("client", session);
  }

  @OnClose
  public void onClose(Session session) {
    sessions.remove("client");
  }

  public void broadcast(Event event) {
    sessions.values().forEach(s -> {
      s.getAsyncRemote().sendObject(event.toString(), result ->  {
        if (result.getException() != null) {
          System.out.println("Unable to send message: " + result.getException());
        }
      });
    });
  }
}
